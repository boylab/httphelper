package com.microape.example.network;

import com.microape.httphelper.rule.HttpListener;
import com.microape.httphelper.rule.HttpProsessor;
import com.microape.httphelper.rule.ReqBean;

/**
 *  * Author：pengl on 2019/4/14 13:11
 *  * Email ：pengle609@163.com
 *  
 */
public class CustomProcessor implements HttpProsessor {

    public CustomProcessor() {
        // TODO: 2019/4/14 新网络框架初始化
    }


    @Override
    public void get(int what, ReqBean reqBean, HttpListener callBack) {
        // TODO: 2019/4/14 按框架协议 发送get请求
    }

    @Override
    public void post(int what, ReqBean reqBean, HttpListener callBack) {
        // TODO: 2019/4/14 按框架协议 发送post请求
    }
}
