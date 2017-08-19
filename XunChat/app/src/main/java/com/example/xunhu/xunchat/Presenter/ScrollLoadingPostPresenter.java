package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.ScrollLoadingPostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.ScrollLoadPostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.ScrollLoadingPostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.scrollLoadingPostView;

/**
 * Created by xunhu on 8/19/2017.
 */

public class ScrollLoadingPostPresenter implements ScrollLoadPostAction,ScrollLoadingPostActionStatus {
    scrollLoadingPostView scrollLoadingPostView;
    ScrollLoadingPostModel scrollLoadingPostModel;
    public ScrollLoadingPostPresenter(scrollLoadingPostView scrollLoadingPostView){
        this.scrollLoadingPostView= scrollLoadingPostView;
        scrollLoadingPostModel = new ScrollLoadingPostModel(this);
    }
    @Override
    public void operateScrollLoadingPost(int id, String timestamp, int type) {
        scrollLoadingPostModel.operateScrollingPostLoading(id,timestamp,type);
    }

    @Override
    public void scrollLoadingSuccess(String msg, int type) {
        scrollLoadingPostView.scrollLoadingSuccess(msg,type);
    }

    @Override
    public void scrollLoadingFail(String msg) {
        scrollLoadingPostView.scrollLoadingFail(msg);
    }
}
