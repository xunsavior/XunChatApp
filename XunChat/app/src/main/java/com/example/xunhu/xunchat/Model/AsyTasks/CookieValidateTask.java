package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.CookieValidateActionStatus;
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
 * Created by xunhu on 6/12/2017.
 */

public class CookieValidateTask extends AsyncTask<String,Void,String> {
    CookieValidateActionStatus cookieValidateActionStatus;
    OkHttpClient client = new OkHttpClient();
    String restfulURL = "";
    public CookieValidateTask(CookieValidateActionStatus cookieValidateActionStatus, String restfulURL){
        this.cookieValidateActionStatus = cookieValidateActionStatus;
        this.restfulURL=restfulURL;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        JSONObject object = new JSONObject();
        try {
            object.put("username",username);
            object.put("password",password);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(restfulURL).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                String feedback =  response.body().string();
                return feedback;
            }else {
                return "connection timed out";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "connection timed out";
        } catch (IOException e) {
            e.printStackTrace();
           return "connection timed out";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("We cannot recognise the username or password you entered.")){
            cookieValidateActionStatus.validateFail();
        }else if (s.equals("connect timed out")){
            cookieValidateActionStatus.validateTimeout();
        }else {
            cookieValidateActionStatus.validateSuccess(s);
        }
    }
}
