package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.SendMessageActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 7/20/2017.
 */

public class SendMessageTask extends AsyncTask<String,Void,String> {
    private SendMessageActionStatus sendMessageActionStatus;
    OkHttpClient client = new OkHttpClient();
    long timestamp=0;
    public SendMessageTask(SendMessageActionStatus sendMessageActionStatus){
        this.sendMessageActionStatus=sendMessageActionStatus;
    }

    @Override
    protected String doInBackground(String... strings) {
        int senderID = Integer.parseInt(strings[0]);
        String senderUsername = strings[1];
        String senderURL = strings[2];
        String targetUsername = strings[3];
        int targetID = Integer.parseInt(strings[4]);
        int messageType = Integer.parseInt(strings[5]);
        String message = strings[6];
        timestamp = Long.parseLong(strings[7]);
        JSONObject object = new JSONObject();
        try {
            object.put("sender_id",senderID);
            object.put("sender_username",senderUsername);
            object.put("sender_url",senderURL);
            object.put("target_username",targetUsername);
            object.put("target_id",targetID);
            object.put("sending_message_type",messageType);
            object.put("message",message);

            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.SEND_MESSAGE).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "send message fail";
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
        if (s.contains("\"success\":1")){
            sendMessageActionStatus.sendMessageSuccessful(timestamp);

        }else{
            sendMessageActionStatus.sendMessageFail(timestamp,s);
        }
    }
}
