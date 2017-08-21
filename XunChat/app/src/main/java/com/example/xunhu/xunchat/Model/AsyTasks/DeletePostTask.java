package com.example.xunhu.xunchat.Model.AsyTasks;

import android.os.AsyncTask;

import com.example.xunhu.xunchat.Presenter.Interfaces.DeletePostActionStatus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xunhu on 8/21/2017.
 */

public class DeletePostTask extends AsyncTask<Integer,Void,String> {
    DeletePostActionStatus deletePostActionStatus;
    public DeletePostTask(DeletePostActionStatus deletePostActionStatus){
        this.deletePostActionStatus=deletePostActionStatus;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        int postID = integers[0];
        int posterID = integers[1];
        JSONObject object = new JSONObject();
        try {
            object.put("post_id",postID);
            object.put("poster_id",posterID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
