package pers.jiangyu.yuweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    /**
     * 与服务器交互的方法
     * @param address 目标网址
     * @param callback okhttp库中自带的回调接口
     */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient(); //创建okHttpClient实例
        Request request = new Request.Builder().url(address).build(); //创建request对象发起数据请求，url中是目标网络地址
        client.newCall(request).enqueue(callback); //处理回调接口信息

    }

}
