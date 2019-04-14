package com.microape.httphelper.rule;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  * Author：pengl on 2019/4/12 08:54
 *  * Email ：pengle609@163.com
 *  
 */
public abstract class HttpCallBack<Result> implements HttpListener, ICallBack<Result> {

    @Override
    public void onStart(int what) {
        // TODO: 2019/4/12 请求开始，等待结果 
    }

    @Override
    public void onSucceed(int what, String result) {
        try {
            Gson gson = new Gson();
            final Class<?> clazz = analysisClassInfo(this);
            final Result objResult = (Result) gson.fromJson(result, clazz);
            onSucceed(what, objResult);

            onFinish(what);
        }catch (Exception e){
            onException(what, new Exception("Json Parse Exception"));
        }
    }

    @Override
    public void onException(int what, Exception e){

        onFailure(what, e);

        onFinish(what);
    }

    @Override
    public void onFinish(int what) {
        // TODO: 2019/4/12 请求结束、取消等待
    }

    private Class<?> analysisClassInfo(Object obj){
        final ParameterizedType genType = (ParameterizedType) obj.getClass().getGenericSuperclass();
        final Type[] actualType = genType.getActualTypeArguments();
        return (Class<?>) actualType[0];
    }
}
