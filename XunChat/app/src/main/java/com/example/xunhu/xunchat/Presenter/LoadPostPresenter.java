package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.MyLoadPostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadPostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadPostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.LoadPostView;

/**
 * Created by xunhu on 8/18/2017.
 */

public class LoadPostPresenter implements LoadPostAction,LoadPostActionStatus {
    LoadPostView loadPostView;
    MyLoadPostModel myLoadPostModel;

    public LoadPostPresenter(LoadPostView loadPostView){
        this.loadPostView= loadPostView;
        myLoadPostModel = new MyLoadPostModel(this);
    }
    @Override
    public void loadPosts(int id) {
        myLoadPostModel.loadPost(id);
    }
    @Override
    public void loadPostSuccess(String msg) {
        loadPostView.loadingPostSuccess(msg);
    }

    @Override
    public void loadPostFail(String msg) {
        loadPostView.loadingPostFail(msg);
    }
}
