package com.example.xunhu.xunchat.Model.AsyTasks;
import android.os.AsyncTask;
import com.example.xunhu.xunchat.Presenter.Interfaces.PostActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xunhu on 8/14/2017.
 */

public class PostPhotoTask extends AsyncTask<String,Void,String> {
    PostActionStatus postActionStatus;
    OkHttpClient client = new OkHttpClient();
    long timestamp=0;
    public PostPhotoTask(PostActionStatus postActionStatus){
       this.postActionStatus=postActionStatus;
    }
    @Override
    protected String doInBackground(String... strings) {
        int userID = Integer.parseInt(strings[0]);
        int postType = Integer.parseInt(strings[1]);
        String postContent = strings[2];
        String time = strings[3];
        String location = strings[4];
        this.timestamp = Long.parseLong(time);
        JSONObject object = new JSONObject();
        try {
            object.put("user_id",userID);
            object.put("post_type",postType);
            object.put("post_content",postContent);
            object.put("timestamp",timestamp);
            object.put("location",location);
            RequestBody requestBody = new FormBody.Builder().add("json",object.toString()).build();
            okhttp3.Request request =new  okhttp3.Request.Builder().
                    url(MainActivity.POST).
                    post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                return "post fail";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Json Error";
        } catch (IOException e) {
            e.printStackTrace();
            return "Network Error "+e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.contains("success")){
            postActionStatus.postSuccess(s);
        }else {
            postActionStatus.postFail(s);
        }
    }
}
