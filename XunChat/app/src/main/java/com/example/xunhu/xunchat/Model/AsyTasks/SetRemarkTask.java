package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.SetRemarkActionStatus;
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

public class SetRemarkTask extends AsyncTask<String,Void,String> {
    SetRemarkActionStatus setRemarkActionStatus;
    OkHttpClient client = new OkHttpClient();
    public SetRemarkTask(SetRemarkActionStatus setRemarkActionStatus){
        this.setRemarkActionStatus=setRemarkActionStatus;
    }
    @Override
    protected String doInBackground(String... strings) {
        int myID = Integer.parseInt(strings[0]);
        int friendID = Integer.parseInt(strings[1]);
        String remark = strings[2];
        JSONObject object = new JSONObject();
        try {
            object.put("my_id",myID);
            object.put("friend_id",friendID);
            object.put("remark",remark);

            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.SET_REMARK).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "Unknown Error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "json error";
        } catch (IOException e) {
            e.printStackTrace();
            return "network error!";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ response "+s);
        if (s.equals("set remark successful")){
            setRemarkActionStatus.setRemarkDone(s);
        }else {
            setRemarkActionStatus.failToSetRemark(s);
        }
    }
}
