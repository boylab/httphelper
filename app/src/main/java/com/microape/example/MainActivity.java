package com.microape.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microape.example.model.WeatherNowReqBean;
import com.microape.example.model.WeatherNowRespBean;
import com.microape.httphelper.HttpHelper;
import com.microape.httphelper.rule.HttpCallBack;

public class MainActivity extends AppCompatActivity {

    private TextView tv_Msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_Msg = findViewById(R.id.tv_Msg);

        final String key = "STAU1dnYMpyjTGUOv";

        findViewById(R.id.btn_GetOrPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final WeatherNowReqBean weatherNowReqBean = new WeatherNowReqBean();
                weatherNowReqBean.setKey(key);
                weatherNowReqBean.setLocation("shanghai");
                HttpHelper.newInstance().get(0x01, weatherNowReqBean, new HttpCallBack<WeatherNowRespBean>() {

                    @Override
                    public void onSucceed(int what, WeatherNowRespBean result) {
                        Log.i(">>>>>microape>>", "onSucceed: "+result.toString());
                        tv_Msg.setText(result.toString());
                    }
                    @Override
                    public void onFailure(int what, Exception e) {
                        Log.i(">>>>>microape>>", "onException: "+e.getMessage());
                    }

                });
            }
        });

        findViewById(R.id.btn_OtherHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发送请求的方式不变，只需在Application中换成 HttpHelper.init(new CustomProcessor());
                final WeatherNowReqBean weatherNowReqBean = new WeatherNowReqBean();
                weatherNowReqBean.setKey(key);
                weatherNowReqBean.setLocation("shanghai");
                HttpHelper.newInstance().get(0x01, weatherNowReqBean, new HttpCallBack<WeatherNowRespBean>() {

                    @Override
                    public void onSucceed(int what, WeatherNowRespBean result) {
                        Log.i(">>>>>microape>>", "onSucceed: "+result.toString());
                        tv_Msg.setText(result.toString());
                    }
                    @Override
                    public void onFailure(int what, Exception e) {
                        Log.i(">>>>>microape>>", "onException: "+e.getMessage());
                    }

                });
            }
        });

    }
}
