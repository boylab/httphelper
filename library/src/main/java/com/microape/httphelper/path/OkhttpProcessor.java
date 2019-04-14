package com.microape.httphelper.path;

import android.os.Handler;
import android.util.Log;

import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.ReqBean;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author：pengl on 2019/4/12 09:22
 * Email ：pengle609@163.com
 *  
 */
public class OkhttpProcessor implements HttpProsessor {

    protected OkHttpClient okHttpClient;
    protected Handler handler;

    public OkhttpProcessor() {
        this.okHttpClient = new OkHttpClient();
        handler = new Handler();
    }

    @Override
    public void get(final int what, ReqBean reqBean, final HttpListener callBack) {

        String url = appendParams(reqBean);
        Log.i(">>>>>microape>>123", "get: "+url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onException(what, e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response.isSuccessful()){
                                final String body = response.body().string();
                                Log.i(">>>>>microape>>", "onResponse: "+body);
                                callBack.onSucceed(what, body);
                            }else {
                                callBack.onException(what, new Exception("respose code is " + response.code()));
                            }
                        }catch (IOException e){
                            callBack.onException(what, e);
                        }
                    }
                });
            }
        });

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
    public void post(final int what, final ReqBean reqBean, final HttpListener callBack) {

        RequestBody requestBody = appendBody(reqBean);
        Request request = new Request
                .Builder()
                .url(reqBean.url())
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onException(what, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String body = response.body().string();
                    Log.i(">>>>>microape>>", "onResponse: "+body);
                    callBack.onSucceed(what, body);
                }else {
                    callBack.onException(what, new Exception("respose code is " + response.code()));
                }
            }
        });
    }

    protected RequestBody appendBody(ReqBean reqBean) {
        FormBody.Builder body = new FormBody.Builder();

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
                        body.add(key, fieldValue);
                    }
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return body.build();
    }
}
