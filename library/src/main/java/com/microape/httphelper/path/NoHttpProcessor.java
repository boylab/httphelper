package com.microape.httphelper.path;

import android.content.Context;

import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.ReqBean;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.lang.reflect.Field;

/**
 *  * Author：pengl on 2019/4/12 13:44
 *  * Email ：pengle609@163.com
 *  
 */
public class NoHttpProcessor implements HttpProsessor {

    protected RequestQueue mQueue = NoHttp.newRequestQueue();

    public NoHttpProcessor(Context context) {
        NoHttp.initialize(context);
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void get(int what, final ReqBean reqBean, final HttpListener callBack) {
        StringRequest mRequest = new StringRequest(reqBean.url(), RequestMethod.GET);
        appendParams(reqBean, mRequest);
        mQueue.add(what, mRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.isSucceed()){
                    callBack.onSucceed(what, response.get());
                }else {
                    callBack.onException(what, response.getException());
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                callBack.onException(what, response.getException());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void post(int what, ReqBean reqBean, final HttpListener callBack) {
        StringRequest mRequest = new StringRequest(reqBean.url(), RequestMethod.POST);
        appendParams(reqBean, mRequest);
        mQueue.add(what, mRequest, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.isSucceed()){
                    callBack.onSucceed(what, response.get());
                }else {
                    callBack.onException(what, response.getException());
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                callBack.onException(what, response.getException());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    protected void appendParams(ReqBean reqBean, StringRequest mRequest) {
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
                        mRequest.add(key, fieldValue);
                    }
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }


}
