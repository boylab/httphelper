package com.microape.httphelper.path;

import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.ReqBean;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleBodyRequest;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;
import com.yanzhenjie.kalle.simple.SimpleUrlRequest;

import java.lang.reflect.Field;

/**
 *  * Author：pengl on 2019/4/12 16:25
 *  * Email ：pengle609@163.com
 *  
 */
public class KalleProcessor implements HttpProsessor {

    public KalleProcessor() {

    }

    @Override
    public void get(final int what, ReqBean reqBean, final HttpListener callBack) {
        final SimpleUrlRequest.Api mApi = Kalle.get(reqBean.url());
        getAppendParams(reqBean, mApi);
        mApi.perform(new SimpleCallback<String>() {

            @Override
            public void onResponse(SimpleResponse<String, String> response) {
                if (response.isSucceed()){
                    callBack.onSucceed(what, response.succeed());
                }else {
                    callBack.onException(what, new Exception(response.failed()));
                }
            }
        });
    }

    @Override
    public void post(final int what, ReqBean reqBean, final HttpListener callBack) {
        final SimpleBodyRequest.Api mApi = Kalle.post(reqBean.url());
        postAppendParams(reqBean, mApi);

        mApi.perform(new SimpleCallback<String>() {
            @Override
            public void onResponse(SimpleResponse<String, String> response) {
                if (response.isSucceed()){
                    callBack.onSucceed(what, response.succeed());
                }else {
                    callBack.onException(what, new Exception(response.failed()));
                }
            }
        });
    }

    protected void getAppendParams(ReqBean reqBean, SimpleUrlRequest.Api mApi) {
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
                        mApi.param(key, fieldValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void postAppendParams(ReqBean reqBean, SimpleBodyRequest.Api mApi) {
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
                        mApi.param(key, fieldValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
