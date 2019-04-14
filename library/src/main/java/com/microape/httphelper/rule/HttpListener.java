package com.microape.httphelper.rule;

/**
 *  * Author：pengl on 2019/4/12 09:02
 *  * Email ：pengle609@163.com
 *  
 */
public interface HttpListener {

    //请求开始
    void onStart(int what);

    //请求成功回调
    void onSucceed(int what, String response);

    //请求失败回调
    void onException(int what, Exception e);

    //请求结束
    void onFinish(int what);

}
