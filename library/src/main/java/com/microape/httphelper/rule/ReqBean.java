package com.microape.httphelper.rule;

/**
 *  * Author：pengl on 2019/4/11 19:41
 *  * Email ：pengle609@163.com
 *  
 */
public abstract class ReqBean implements ReqProtocol{

    @Override
    public String baseUrl() {
        return null;
    }

    @Override
    public String url() {
        return null;
    }


}
