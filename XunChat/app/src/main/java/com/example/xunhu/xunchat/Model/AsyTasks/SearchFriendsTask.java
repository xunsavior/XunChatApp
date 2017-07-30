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

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 6/21/2017.
 */

public class SearchFriendsTask extends AsyncTask<String,Void,String>{
    SearchFriendsActionStatus searchFriendsActionStatus;
    String restfulURL = MainActivity.SEARCH_FRIEND;
    OkHttpClient client = new OkHttpClient();
    public SearchFriendsTask(SearchFriendsActionStatus searchFriendsActionStatus){
        this.searchFriendsActionStatus=searchFriendsActionStatus;
    }
    @Override
    protected String doInBackground(String... params) {
        String targetUsername=params[0];
        try {
            JSONObject object = new JSONObject();
            object.put("targetUsername",targetUsername);
            object.put("my_id",MainActivity.me.getId());
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
        System.out.println("@ respond "+s);
        if (s!=null){
            if (s.equals("Network Error")){
                searchFriendsActionStatus.searchFriendFail(s);
            }else if (s.equals("The user does not exist!")){
                searchFriendsActionStatus.searchFriendFail(s);
            } else {
                searchFriendsActionStatus.searchFriendsSuccess(s);
            }
        }
    }
}
