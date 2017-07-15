package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.DeleteFriendActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 7/14/2017.
 */

public class DeleteFriendTask extends AsyncTask<Integer,Void,String> {
    OkHttpClient client = new OkHttpClient();
    DeleteFriendActionStatus deleteFriendActionStatus;

    public DeleteFriendTask(DeleteFriendActionStatus deleteFriendActionStatus){
        this.deleteFriendActionStatus=deleteFriendActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int myID = integers[0];
        int friendID = integers[1];

        JSONObject object = new JSONObject();
        try {
            object.put("my_id",myID);
            object.put("friend_id",friendID);

            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.DELETE_FRIEND).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "Unknown Error";
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
        if (s.equals("delete successful")){
            deleteFriendActionStatus.deleteSuccessful(s);
        }else {
            deleteFriendActionStatus.deleteFail("Action fail!");
        }
    }
}
