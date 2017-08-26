package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.FriendRespondActionStatus;
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
 * Created by xunhu on 7/3/2017.
 */

public class FriendRequestRespondTask extends AsyncTask<String,Void,String> {
    FriendRespondActionStatus friendRespondActionStatus;
    String restfulURL = MainActivity.FRIEND_REQUEST_RESPOND;
    public FriendRequestRespondTask( FriendRespondActionStatus friendRespondActionStatus){
        this.friendRespondActionStatus = friendRespondActionStatus;
    }

    @Override
    protected String doInBackground(String... strings) {
        int targetID = Integer.parseInt(strings[0]);
        int responderID = Integer.parseInt(strings[1]);
        String responderUsername =strings[2];
        String responderUrl = strings[3];
        String responderGender = strings[4];
        String responderRegion = strings[5];
        String responderWhatsup = strings[6];
        int responderAge = Integer.parseInt(strings[7]);
        String responderNickname = strings[8];
        String respondRemark = strings[9];

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);

            JSONObject object = new JSONObject();
            object.put("target_id",targetID);
            object.put("responder_id",responderID);
            object.put("responder_username",responderUsername);
            object.put("responder_url",responderUrl);
            object.put("responder_gender",responderGender);
            object.put("responder_region",responderRegion);
            object.put("responder_whatsup",responderWhatsup);
            object.put("responder_age",responderAge);
            object.put("responder_nickname",responderNickname);
            object.put("responder_remark",respondRemark);
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
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error";
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s!=null){
            if (s.contains("\"success\":1")){
                friendRespondActionStatus.respondSuccess("Friend Request Accepted Successfully");
            }else {
                friendRespondActionStatus.respondFail(s);
            }
        }
    }
}
