package com.microape.httphelper;

import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.ReqBean;

/**
 *  * Author：pengl on 2019/4/11 19:31
 *  * Email ：pengle609@163.com
 *  
 */
public class HttpHelper implements HttpProsessor {

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

    private static HttpProsessor mHttpProsessor;
    public static void init(HttpProsessor httpProsessor){
        mHttpProsessor = httpProsessor;
    }


    @Override
    public void get(int what, ReqBean reqBean, HttpListener callBack) {
        //String finalUrl = appendParams(reqProtocol);
        callBack.onStart(what);
        mHttpProsessor.get(what, reqBean, callBack);
    }

    @Override
    public void post(int what, ReqBean reqBean, HttpListener callBack) {
        //String finalUrl = appendParams(reqProtocol);
        callBack.onStart(what);
        mHttpProsessor.post(what, reqBean, callBack);
    }

}
