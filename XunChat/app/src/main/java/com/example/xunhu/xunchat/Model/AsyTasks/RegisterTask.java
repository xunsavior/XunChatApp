package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.RegisterActionStatus;
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
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 6/10/2017.
 */

public class RegisterTask extends AsyncTask<String,Void,String> {
       RegisterActionStatus actionStatus;
       String restfulURL = "";
       OkHttpClient client = new OkHttpClient();
    public RegisterTask(RegisterActionStatus actionStatus,String restfulURL){
        this.actionStatus=actionStatus;
        this.restfulURL=restfulURL;
    }
    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String nickname = params[1];
        String password = params[2];
        String email = params[3];
        String image_url = params[4];
        String birthday = params[5];
        String token = params[6];
        String gender = params[7];
        String region = params[8];
        String QRCode = params[9];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = Integer.parseInt(birthday.split("/")[2]);
        int age = currentYear - birthYear;
        try {
            JSONObject object = new JSONObject();
            object.put("username",username);
            object.put("nickname",nickname);
            object.put("password",password);
            object.put("email",email);
            object.put("url",image_url);
            object.put("birthday",birthday);
            object.put("age",age);
            object.put("token",token);
            object.put("gender",gender);
            object.put("region",region);
            object.put("QRCode",QRCode);
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
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("done")){
            actionStatus.registerSuccess(s);
        }else {
            actionStatus.registerFail(s);
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
