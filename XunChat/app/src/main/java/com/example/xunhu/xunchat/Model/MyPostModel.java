package com.example.xunhu.xunchat.Model;

import android.content.Context;

import com.example.xunhu.xunchat.Model.AsyTasks.PostPhotoTask;
import com.example.xunhu.xunchat.Model.Interfaces.CURDPostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.PostActionStatus;

import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 8/14/2017.
 */
public class MyPostModel implements CURDPostOptions {
    PostActionStatus actionStatus;
    public Context context;

    public MyPostModel(Context context){
        this.context = context;
    }
    public void setActionStatus(PostActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }
    @Override
    public void attemptPost(int userID, int postType, String postContent, String timestamp,String location) {
        new PostPhotoTask(actionStatus).execute(String.valueOf(userID),String.valueOf(postType),postContent,timestamp,location);
    }
}
