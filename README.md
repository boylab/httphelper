# HttpHelper简介

* 1、 纯面向对象（返回json字符格式），传入实体类，返回实体类
* 2、 可一行代码切换支持okhttp、volley、nohttp、kalle、xutils等框架，
* 3、 只写了get、post，也可扩展添加新的网络框架


# 使用

### 1、使用Gradle构建时添加一下依赖即可:
```
compile 'com.microape:httphelper:0.0.2'
```
### 2、需要的权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
### 3、初始化
```
// 在application的onCreate中初始化，别忘了在AndroidManifest.xml添加android:name=".MyApp"
@Override
public void onCreate() {
    super.onCreate();

	//okhttp 请求
    HttpHelper.init(new OkhttpProcessor());

	//还有KalleProcessor、NoHttpProcessor、VolleyProcessor、XutilsProcessor
}
```
### 4、添加请求
```
//请求对象实体
public class WeatherNowReqBean extends ReqBean{···请求参数，看成实体属性···}

//返回的对象实体
public class WeatherNowRespBean extends RespBean{···返回json to 实体类···}

//发送网络请求
final WeatherNowReqBean weatherNowReqBean = new WeatherNowReqBean();
·····给属性赋值····
weatherNowReqBean.setKey(key);
weatherNowReqBean.setLocation("shanghai");
HttpHelper.newInstance().get(0x01, weatherNowReqBean, new HttpCallBack<WeatherNowRespBean>() {

    @Override
    public void onSucceed(int what, WeatherNowRespBean result) {
        Log.i(">>>>>microape>>", "onSucceed: "+result.toString());
    }
    @Override
    public void onFailure(int what, Exception e) {
        Log.i(">>>>>microape>>", "onException: "+e.getMessage());
    }

});
```

### 关于作者
```
QQ : 695344490
Email : pengle609@163.com
```
