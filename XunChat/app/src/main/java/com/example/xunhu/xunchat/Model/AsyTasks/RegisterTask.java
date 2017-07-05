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

/**
 * Created by xunhu on 6/10/2017.
 */

public class RegisterTask extends AsyncTask<String,Void,String> {
       RegisterActionStatus actionStatus;
       String restfulURL = MainActivity.REGISTER;

    public RegisterTask(RegisterActionStatus actionStatus){
        this.actionStatus=actionStatus;
    }
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
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
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);

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

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String postData = URLEncoder.encode("json","UTF-8")+"="+URLEncoder.encode(object.toString(),"UTF-8");
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result+=line;
            }
            bufferedReader.close();
            inputStream.close();
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
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
