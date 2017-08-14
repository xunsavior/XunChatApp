package com.example.xunhu.xunchat.Presenter;

import android.content.Context;

import com.example.xunhu.xunchat.Model.MyPostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.PostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.PostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.PostPhotoView;
import org.androidannotations.annotations.EBean;

/**
 * Created by xunhu on 8/14/2017.
 */
public class PostActionPresenter implements PostAction,PostActionStatus {
    public Context context;
    PostPhotoView postPhotoView;
    MyPostModel myPostModel;
    public PostActionPresenter(Context context){
        this.context=context;
        myPostModel = new MyPostModel(context);
        myPostModel.setActionStatus(this);
    }
    public void setPostPhotoView(PostPhotoView postPhotoView) {
        this.postPhotoView = postPhotoView;
    }
    @Override
    public void operatePost(int id, int postType, String postContent, String timestamp,String location) {
        myPostModel.attemptPost(id,postType,postContent,timestamp,location);
    }
    @Override
    public void postSuccess(String msg) {
        postPhotoView.postSuccess(msg);
    }
    @Override
    public void postFail(String msg) {
        postPhotoView.postFail(msg);
    }
}
