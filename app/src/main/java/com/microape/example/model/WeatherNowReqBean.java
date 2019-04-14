package com.microape.example.model;

/**
 *  Author：pengl on 2019/4/12 10:24
 *  Email ：pengle609@163.com
 *  
 */
public class WeatherNowReqBean extends ReqBaseBean {

    //key=your_api_key&location=beijing&language=zh-Hans&unit=c
    private String key;
    private String location;
    private String language = "zh-Hans";    //默认简体中文
    private String unit = "c";              //默认设置温度

    public WeatherNowReqBean() {
    }

    public WeatherNowReqBean(String key, String location, String language, String unit) {
        this.key = key;
        this.location = location;
        this.language = language;
        this.unit = unit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String url() {
        return baseUrl().concat("weather/now.json");
    }

}
