package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.SearchFriendsActionStatus;
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
 * Created by xunhu on 6/21/2017.
 */

public class SearchFriendsTask extends AsyncTask<String,Void,String>{
    SearchFriendsActionStatus searchFriendsActionStatus;
    String restfulURL = MainActivity.domain_url+"search.php";
    public SearchFriendsTask(SearchFriendsActionStatus searchFriendsActionStatus){
        this.searchFriendsActionStatus=searchFriendsActionStatus;
    }
    @Override
    protected String doInBackground(String... params) {
        String nickname=params[0];
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(5000);

            JSONObject object = new JSONObject();
            object.put("nickname",nickname);
            object.put("username",MainActivity.me.getUsername());
            System.out.println("@ object "+object.toString());
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
        if (s!=null){
            if (s.equals("Network Error")){
                searchFriendsActionStatus.searchFriendFail(s);
            }else {
                searchFriendsActionStatus.searchFriendsSuccess(s);
            }
        }
    }
}
