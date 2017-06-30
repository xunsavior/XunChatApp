package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.UpdateProfileActionStatus;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by xunhu on 6/17/2017.
 */

public class UpdateProfileTask extends AsyncTask<String,Void,String> {
    String restURL = MainActivity.domain_url+"edit_profile.php";
    UpdateProfileActionStatus updateProfileActionStatus;

    public UpdateProfileTask(UpdateProfileActionStatus updateProfileActionStatus){
        this.updateProfileActionStatus=updateProfileActionStatus;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection=null;
        String username = params[0];
        String title = params[1];
        String content = params[2];
        try {
            URL url = new URL(restURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);

            JSONObject object = new JSONObject();
            object.put("username",username);
            object.put("title",title);
            object.put("content",content);
            System.out.println("@ update "+object.toString());
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
        } catch (JSONException e) {
            e.printStackTrace();
            return "JSON "+e.getMessage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "URL "+e.getMessage();
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "protocol "+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "IO "+e.getMessage();
        } finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(String response) {
        System.out.println("@ response "+response);
        if (response!=null){
            if (response.contains("title")){
                updateProfileActionStatus.updateSuccess(response);
            }else {
                updateProfileActionStatus.updateFail(response);
            }
        }

    }
}
