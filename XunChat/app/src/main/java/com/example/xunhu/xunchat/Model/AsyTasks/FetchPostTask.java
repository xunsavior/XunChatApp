package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.LoadPostActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 8/18/2017.
 */

public class FetchPostTask extends AsyncTask<Integer,Void,String> {
    LoadPostActionStatus loadPostActionStatus;
    OkHttpClient client = new OkHttpClient();
    public FetchPostTask(LoadPostActionStatus loadPostActionStatus){
        this.loadPostActionStatus=loadPostActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int userID = integers[0];
        JSONObject object = new JSONObject();
        try {
            object.put("user_id",userID);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.FETCH_POSTS).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "fail to load posts";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Json error";
        } catch (IOException e) {
            e.printStackTrace();
            return "network error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
        if (s.contains("post_content")){
            loadPostActionStatus.loadPostSuccess(s);
        }else {
            loadPostActionStatus.loadPostFail(s);
        }
    }
}
