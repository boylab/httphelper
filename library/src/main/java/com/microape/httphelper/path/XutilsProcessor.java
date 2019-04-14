package com.microape.httphelper.path;

import android.app.Application;

import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.ReqBean;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;

/**
 *  * Author：pengl on 2019/4/12 13:43
 *  * Email ：pengle609@163.com
 *  
 */

public class XutilsProcessor implements HttpProsessor {

    public XutilsProcessor(Application app) {
        x.Ext.init(app);
    }

    @Override
    public void get(final int what, ReqBean reqBean, final HttpListener callBack) {
        RequestParams mParams = new RequestParams(reqBean.url());
        //mParams.setMultipart(true);
        mParams.setMethod(HttpMethod.GET);

        appendParams(reqBean, mParams);
        Callback.Cancelable post = x.http().get(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSucceed(what, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onException(what, new Exception(ex.getMessage()));
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onException(what, cex);
            }

            @Override
            public void onFinished() {
            }
        });

    }

    @Override
    public void post(final int what, ReqBean reqBean, final HttpListener callBack) {
        RequestParams mParams = new RequestParams(reqBean.url());
        //mParams.setMultipart(true);
        mParams.setMethod(HttpMethod.POST);

        appendParams(reqBean, mParams);
        Callback.Cancelable post = x.http().post(mParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callBack.onSucceed(what, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onException(what, new Exception(ex.getMessage()));
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onException(what, cex);
            }

            @Override
            public void onFinished() {
            }
        });
    }

    protected void appendParams(ReqBean reqBean, RequestParams mParams) {
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
                        mParams.addBodyParameter(key, fieldValue);
                    }
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
