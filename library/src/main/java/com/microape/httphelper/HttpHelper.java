package com.microape.httphelper;

/**
 *  * Author：pengl on 2019/4/11 19:31
 *  * Email ：pengle609@163.com
 *  
 */
public class HttpHelper {

    private static HttpHelper httpHelper = null;

    public HttpHelper() {
    }

    public static HttpHelper newInstance(){
        synchronized (HttpHelper.class){
            if (httpHelper == null){
                httpHelper = new HttpHelper();
            }
        }
        return httpHelper;
    }

}
