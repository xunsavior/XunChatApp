package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.LoadWhoLikePostActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 8/21/2017.
 */

public class LoadWhoLikePostTask extends AsyncTask<Integer,Void,String> {
    LoadWhoLikePostActionStatus loadWhoLikePostActionStatus;
    OkHttpClient client = new OkHttpClient();
    public LoadWhoLikePostTask(LoadWhoLikePostActionStatus loadWhoLikePostActionStatus){
        this.loadWhoLikePostActionStatus=loadWhoLikePostActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int postID = integers[0];
        JSONObject object = new JSONObject();
        try {
            object.put("post_id",postID);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.LOAD_WHO_LIKE_POST).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "fail to load data";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.contains("user_id")){
            loadWhoLikePostActionStatus.loadSuccess(s);
        }else {
            loadWhoLikePostActionStatus.loadFail(s);
        }
    }
}
