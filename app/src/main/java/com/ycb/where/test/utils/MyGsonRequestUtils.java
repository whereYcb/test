package com.ycb.where.test.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by 袁从斌-where on 2016/5/20.
 * 自定义网络请求
 */
public class MyGsonRequestUtils<T> extends Request<T> {
    //具体类型的bean
    private Class<T> clazz;
    private Response.Listener mListener;
    private Context mContext;
    public MyGsonRequestUtils(int method, String url, Response.ErrorListener listener, Class<T> clazz,
                              Response.Listener mListener, Context context) {
        super(method, url, listener);
        this.clazz = clazz;
        this.mListener=mListener;
        this.mContext=context;
    }

    /**
     * 处理网络请求
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(mContext,"请求网络失败",Toast.LENGTH_SHORT).show();
            parsed = new String(response.data);
        }

        T obj = null;
        try {
            Gson gson = new Gson();
            obj = gson.fromJson(parsed, clazz);
            Log.e("TAG",obj.toString());
            return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }

    }

    /**
     * 传递响应结果
     *
     * @param response
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
