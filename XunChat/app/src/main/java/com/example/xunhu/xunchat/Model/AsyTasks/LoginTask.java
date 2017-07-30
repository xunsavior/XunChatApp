package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.LoginActionStatus;
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

public class LoginTask extends AsyncTask<String,Void,String> {
    LoginActionStatus loginActionStatus;
    String restfulURL = "";
    OkHttpClient client = new OkHttpClient();
    public LoginTask(LoginActionStatus loginActionStatus,String restfulURL){
        this.loginActionStatus=loginActionStatus;
        this.restfulURL=restfulURL;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        String token = params[2];
        JSONObject object = new JSONObject();
        try {
            object.put("username",username);
            object.put("password",password);
            object.put("token",token);
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Network Error";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@ respond "+s);
        if (s!=null){
            if (s.equals("We can't recognise the username or password you entered.") ||
                    s.equals("Network Error")){
                loginActionStatus.loginFail(s);
            }else {
                loginActionStatus.loginSuccess(s);
            }
        }

    }
}
