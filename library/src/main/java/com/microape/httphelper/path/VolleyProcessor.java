package com.microape.httphelper.path;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.ReqBean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *  * Author：pengl on 2019/4/12 09:21
 *  * Email ：pengle609@163.com
 *  
 */
public class VolleyProcessor implements HttpProsessor {

    protected RequestQueue mQueue = null;

    public VolleyProcessor(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void get(final int what, ReqBean reqBean, final HttpListener callBack) {
        String url = appendParams(reqBean);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBack.onSucceed(what, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onException(what, new Exception(error.getMessage()));
                    }
                });
        mQueue.add(stringRequest);
    }

    protected String appendParams(ReqBean reqBean) {
        String appendUrl = reqBean.url();

        Class clazz = reqBean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            final int length = fields.length;
            for (int i = 0; i < length; i++) {
                appendUrl = appendUrl.concat(i == 0 ? "?" : "&");

                Field field = fields[i];
                String key = field.getName();

                field.setAccessible(true);
                Object object = field.get(reqBean);
                if (object != null) {
                    String fieldValue = object.toString();
                    if (fieldValue != null) {
                        appendUrl = appendUrl.concat(key).concat("=").concat(fieldValue);
                    }
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return appendUrl;
    }

    @Override
    public void post(final int what, ReqBean reqBean, final HttpListener callBack) {
        Map<String, String> requestBody = appendBody(reqBean);

        StringPostRequest stringRequest = new StringPostRequest(Request.Method.POST, reqBean.url(), requestBody,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBack.onSucceed(what, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onException(what, new Exception(error.getMessage()));
                    }
                });

        mQueue.add(stringRequest);
    }

    protected Map<String, String> appendBody(ReqBean reqBean) {
        Map<String, String> body = new HashMap<>();

        Class clazz = reqBean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                String key = field.getName();

                field.setAccessible(true);
                Object object = field.get(reqBean);
                if (object != null) {
                    String fieldValue = object.toString();
                    if (fieldValue != null) {
                        body.put(key, fieldValue);
                    }
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return body;
    }

    private class StringPostRequest extends StringRequest{

        Map<String, String> requestBody ;

        public StringPostRequest(int method, String url, Map<String, String> requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
            this.requestBody = requestBody;
        }

        public StringPostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public StringPostRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        public void setRequestBody(Map<String, String> requestBody) {
            this.requestBody = requestBody;
        }

        /**
         * 当发出POST请求的时候，Volley会尝试调用StringRequest的父类——Request中的getParams()方法来获取POST参数
         * @return
         * @throws AuthFailureError
         */
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            if (requestBody.isEmpty()){
                return super.getParams();
            }else {
                return requestBody;
            }


        }
    }


}
