package com.microape.example.model;

import com.microape.httphelper.rule.ReqBean;

/**
 *  Author：pengl on 2019/4/12 10:26
 *  Email ：pengle609@163.com
 *  
 */
public abstract class ReqBaseBean extends ReqBean {
    @Override
    protected String baseUrl() {
        return "https://api.seniverse.com/v3/";
    }

}
