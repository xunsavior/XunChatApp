package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.LoadWhoLikePostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadWhoLikePostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadWhoLikePostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.LoadingWhoLikePostView;

/**
 * Created by xunhu on 8/21/2017.
 */

public class LoadWhoLikePostPresenter implements LoadWhoLikePostAction,LoadWhoLikePostActionStatus {
    LoadingWhoLikePostView loadingWhoLikePostView;
    LoadWhoLikePostModel loadWhoLikePostModel;
    public LoadWhoLikePostPresenter(LoadingWhoLikePostView loadingWhoLikePostView){
        this.loadingWhoLikePostView=loadingWhoLikePostView;
        loadWhoLikePostModel = new LoadWhoLikePostModel(this);
    }
    @Override
    public void loadFail(String msg) {
        loadingWhoLikePostView.loadingFail(msg);
    }

    @Override
    public void loadSuccess(String msg) {
        loadingWhoLikePostView.loadingPost(msg);
    }

    @Override
    public void loadWhoLikePost(int postID) {
        loadWhoLikePostModel.loadWhoLikePost(postID);
    }
}
