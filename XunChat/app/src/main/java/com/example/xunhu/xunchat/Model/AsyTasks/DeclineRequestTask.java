package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.UserDeclineRequestModel;
import com.example.xunhu.xunchat.View.MainActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by xunhu on 7/8/2017.
 */

public class DeclineRequestTask extends AsyncTask<Integer,Void,String>{
    UserDeclineRequestModel userDeclineRequestModel;
    OkHttpClient client = new OkHttpClient();
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public DeclineRequestTask(UserDeclineRequestModel userDeclineRequestModel){
        this.userDeclineRequestModel = userDeclineRequestModel;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int myID = integers[0];
        int targetID = integers[1];
        JSONObject object = new JSONObject();
        try {
            object.put("my_id",myID);
            object.put("target_id",targetID);
            RequestBody requestBody = RequestBody.create(JSON,object.toString());
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(MainActivity.DECLINE_REQUEST).post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "fail to delete";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "JSON error";
        } catch (IOException e) {
            e.printStackTrace();
            return "network error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
    }
}
