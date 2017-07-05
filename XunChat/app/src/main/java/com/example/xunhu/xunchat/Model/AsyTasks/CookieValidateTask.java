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

/**
 * Created by xunhu on 6/12/2017.
 */

public class CookieValidateTask extends AsyncTask<String,Void,String> {
    CookieValidateActionStatus cookieValidateActionStatus;
    String restfulURL = MainActivity.VALIDATE_LOGIN;
    public CookieValidateTask(CookieValidateActionStatus cookieValidateActionStatus){
        this.cookieValidateActionStatus = cookieValidateActionStatus;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(5000);
            JSONObject object = new JSONObject();
            object.put("username",username);
            object.put("password",password);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String postData =  URLEncoder.encode("json","UTF-8")+"="+URLEncoder.encode(object.toString(),"UTF-8");
            writer.write(postData);
            writer.flush();
            writer.close();
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
            return "connect timed out";
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
        if (s.equals("We can't recognise the username or password you entered.")){
            cookieValidateActionStatus.validateFail();
        }else if (s.equals("connect timed out")){
            cookieValidateActionStatus.validateTimeout();
        }else {
            cookieValidateActionStatus.validateSuccess(s);
        }
    }
}
