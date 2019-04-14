package com.microape.httphelper.rule;

/**
 *  * Author：pengl on 2019/4/12 09:02
 *  * Email ：pengle609@163.com
 *  
 */
interface ICallBack<T> {

    /**
     * @param what
     * @param result
     */
    public abstract void onSucceed(int what, T result);

    /**
     * @param what
     * @param e
     */
    public abstract void onFailure(int what, Exception e);

}
