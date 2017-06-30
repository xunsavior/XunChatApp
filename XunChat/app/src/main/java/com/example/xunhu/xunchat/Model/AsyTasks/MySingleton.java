package com.example.xunhu.xunchat.Model.AsyTasks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by xunhu on 6/14/2017.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private static Context context;
    private RequestQueue requestQueue;

    private MySingleton(Context context){
        this.context = context;
        requestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getmInstance(Context context){
        if (mInstance==null){
            mInstance=new MySingleton(context);
        }
        return mInstance;
    }
    public void addImageRequestToRequestQueue(ImageRequest request){
        requestQueue.add(request);
    }

    public void addStringRequestToRequestQueue(StringRequest request){
        requestQueue.add(request);
    }

}
