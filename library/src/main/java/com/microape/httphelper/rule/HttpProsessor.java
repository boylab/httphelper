package com.microape.httphelper.rule;

/**
 *  * Author：pengl on 2019/4/11 19:37
 *  * Email ：pengle609@163.com
 *  
 */
public interface HttpProsessor {

    void get(int what, ReqBean reqBean, HttpListener callBack);

    void post(int what, ReqBean reqBean, HttpListener callBack);

}
