package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.LikePostActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 8/20/2017.
 */

public class LikePostTask extends AsyncTask<Integer,Void,String> {
    LikePostActionStatus likePostActionStatus;
    OkHttpClient client = new OkHttpClient();
    int postID= -1;
    int type=-1;
    public LikePostTask(LikePostActionStatus likePostActionStatus){
        this.likePostActionStatus = likePostActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int postID = integers[0];
        int userID = integers[1];
        this.type=integers[2];
        this.postID = postID;
        JSONObject object = new JSONObject();
        try {
            object.put("post_id",postID);
            object.put("user_id",userID);
            object.put("type",type);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.LIKE_POST).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "fail to like the post!";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Error"+e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
        if (s.contains("successful")){
            likePostActionStatus.likedSuccess(s,postID,type);
        }else {
            likePostActionStatus.likedFail(s);
        }
    }
}
