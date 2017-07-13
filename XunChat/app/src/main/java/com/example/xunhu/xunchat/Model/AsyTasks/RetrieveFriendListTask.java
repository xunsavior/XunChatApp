package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.RetrieveFriendListActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 7/13/2017.
 */

public class RetrieveFriendListTask extends AsyncTask<Integer,Void,String> {
    OkHttpClient client = new OkHttpClient();
    RetrieveFriendListActionStatus retrieveFriendListActionStatus;
    public RetrieveFriendListTask(RetrieveFriendListActionStatus retrieveFriendListActionStatus){
        this.retrieveFriendListActionStatus=retrieveFriendListActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int myID = integers[0];
        JSONObject object = new JSONObject();
        try {
            object.put("user_id",myID);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.RETRIEVE_FRIEND_LIST).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "Fail to load your friend list!";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Json error";
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ response "+s);
        switch (s){
            case "Fail to load your friend list!":
                retrieveFriendListActionStatus.retrieveFriendListFail(s);
                break;
            case "you currently have no friend!":
                retrieveFriendListActionStatus.retrieveFriendListFail(s);
                break;
            case "Network Error":
                retrieveFriendListActionStatus.retrieveFriendListFail(s);
                break;
            case "Json error":
                retrieveFriendListActionStatus.retrieveFriendListFail(s);
                break;
            default:
                retrieveFriendListActionStatus.retrieveFriendListSuccessful(s);
                break;
        }

    }
}
