package com.microape.example;

import android.app.Application;

import com.microape.httphelper.HttpHelper;
import com.microape.httphelper.path.OkhttpProcessor;

/**
 * Author：pengl on 2019/4/12 10:01
 * Email ：pengle609@163.com
 *  
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Kalle 网络请求
        //HttpHelper.init(new KalleProcessor());

        //NoHttp 网络请求
        //HttpHelper.init(new NoHttpProcessor(this));

        //okhttp 请求
        HttpHelper.init(new OkhttpProcessor());

        //volley 网络请求
        //HttpHelper.init(new VolleyProcessor(this));

        //Xutils 网络请求
        //HttpHelper.init(new XutilsProcessor(this));

        //使用自行接入的网络框架
        //HttpHelper.init(new CustomProcessor());

    }
}
