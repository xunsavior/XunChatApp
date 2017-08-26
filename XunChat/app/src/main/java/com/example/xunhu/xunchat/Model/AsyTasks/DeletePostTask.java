package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Model.Entities.Post;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeletePostActionStatus;
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

public class DeletePostTask extends AsyncTask<Post,Void,String> {
    DeletePostActionStatus deletePostActionStatus;
    OkHttpClient client = new OkHttpClient();
    Post post=null;
    public DeletePostTask(DeletePostActionStatus deletePostActionStatus){
        this.deletePostActionStatus=deletePostActionStatus;
    }
    @Override
    protected String doInBackground(Post... posts) {
        post = posts[0];
        JSONObject object = new JSONObject();
        try {
            object.put("post_id",post.getPostID());
            object.put("poster_id",post.getPosterID());
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.DELETE_POST).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "Unknown Error";
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
        if (s.contains("The post has been deleted!")){
            deletePostActionStatus.deletePostSuccess(post);
        }else {
            deletePostActionStatus.deletePostFail(s);
        }
    }
}
