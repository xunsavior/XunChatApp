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

/**
 * Created by xunhu on 6/27/2017.
 */

public class LogoutTask extends AsyncTask<String,Void,String> {
    String restfulURL = MainActivity.LOGOUT;
    LogoutActionStatus logoutActionStatus;

    public LogoutTask(LogoutActionStatus logoutActionStatus){
        this.logoutActionStatus = logoutActionStatus;
    }
    @Override
    protected String doInBackground(String... strings) {
        String username = strings[0];
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            JSONObject object = new JSONObject();
            object.put("username",username);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String postData =  URLEncoder.encode("json","UTF-8")+"="+URLEncoder.encode(object.toString(),"UTF-8");
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
            return "incorrect url";
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (JSONException e) {
            e.printStackTrace();
            return "JSON Error";
        } finally {
                if (httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
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
