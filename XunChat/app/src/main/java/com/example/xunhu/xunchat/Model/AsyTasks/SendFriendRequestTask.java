package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.SendFriendRequestActionStatus;
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
 * Created by xunhu on 6/23/2017.
 */

public class SendFriendRequestTask extends AsyncTask<String, Void,String> {
    SendFriendRequestActionStatus sendFriendRequestActionStatus;
    String restfulURL = MainActivity.domain_url+"FriendRequest.php";
    public SendFriendRequestTask(SendFriendRequestActionStatus sendFriendRequestActionStatus){
        this.sendFriendRequestActionStatus=sendFriendRequestActionStatus;
    }
    @Override
    protected String doInBackground(String... strings) {
        String senderUsername = strings[0];
        String senderNickname = strings[1];
        int senderAge = Integer.parseInt(strings[2]);
        String senderGender = strings[3];
        String senderRegion = strings[4];
        String senderUrl = strings[5];
        String senderWhatsup= strings[6];
        String targetToken = strings[7];
        String extras = strings[8];

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(restfulURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);

            JSONObject object = new JSONObject();
            object.put("sender_username",senderUsername);
            object.put("sender_nickname",senderNickname);
            object.put("sender_age",senderAge);
            object.put("sender_gender",senderGender);
            object.put("sender_region",senderRegion);
            object.put("sender_url",senderUrl);
            object.put("sender_whatsup",senderWhatsup);
            object.put("target_token",targetToken);
            object.put("sender_extras",extras);

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
            System.out.println("@ respond "+s);
            if (s.contains("\"success\":1")){
                sendFriendRequestActionStatus.onSentSuccess("Your friend request has been sent successfully!");
            }else {
                sendFriendRequestActionStatus.onSentFail("Fail to send your friend request!");
            }
        }
    }
}
