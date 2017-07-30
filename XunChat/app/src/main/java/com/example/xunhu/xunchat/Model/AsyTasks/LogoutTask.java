package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.LogoutActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 6/27/2017.
 */

public class LogoutTask extends AsyncTask<String,Void,String> {
    String restfulURL = "";
    LogoutActionStatus logoutActionStatus;
    OkHttpClient client = new OkHttpClient();

    public LogoutTask(LogoutActionStatus logoutActionStatus, String restfulURL){
        this.logoutActionStatus = logoutActionStatus;
        this.restfulURL=restfulURL;
    }
    @Override
    protected String doInBackground(String... strings) {
        String username = strings[0];
        HttpURLConnection httpURLConnection = null;
        JSONObject object = new JSONObject();
        try {
            object.put("username",username);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(restfulURL).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                String feedback =  response.body().string();
                return feedback;
            }else {
                return "network error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "network error";
        } catch (IOException e) {
            e.printStackTrace();
            return "network error";
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
        if (s!=null){
            if (s.equals("logout successfully!")){
                logoutActionStatus.LogoutSuccess(s);
            }else {
                logoutActionStatus.LogoutFail(s);
            }
        }
    }
}
