package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.ScrollLoadingPostActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 8/19/2017.
 */

public class ScrollLoadingPostTask extends AsyncTask<String,Void,String> {
    ScrollLoadingPostActionStatus scrollLoadingPostActionStatus;
    int type = -1;
    OkHttpClient client = new OkHttpClient();
    public ScrollLoadingPostTask(ScrollLoadingPostActionStatus scrollLoadingPostActionStatus){
        this.scrollLoadingPostActionStatus=scrollLoadingPostActionStatus;
    }
    @Override
    protected String doInBackground(String... strings) {
        int userID = Integer.parseInt(strings[0]);
        long timestamp = Long.parseLong(strings[1]);
         type = Integer.parseInt(strings[2]);
        JSONObject object = new JSONObject();
        try {
            object.put("user_id",userID);
            object.put("timestamp",timestamp);
            object.put("type",type);
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
            return "network error "+e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
        if (s.contains("post_content")){
            scrollLoadingPostActionStatus.scrollLoadingSuccess(s,type);
        }else {
            scrollLoadingPostActionStatus.scrollLoadingFail(s);
        }
    }
}
