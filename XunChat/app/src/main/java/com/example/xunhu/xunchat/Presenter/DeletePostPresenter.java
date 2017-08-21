package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.DeletePostModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeletePostAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeletePostActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.DeletePostView;

/**
 * Created by xunhu on 8/21/2017.
 */

public class DeletePostPresenter implements DeletePostAction,DeletePostActionStatus {
    DeletePostView deletePostView;
    DeletePostModel deletePostModel;
    public DeletePostPresenter(DeletePostView deletePostView){
        this.deletePostView=deletePostView;
        deletePostModel = new DeletePostModel(this);
    }
    @Override
    public void deletePostAction(int postID, int posterID) {
        deletePostModel.performDeletePost(postID,posterID);
    }
    @Override
    public void deletePostFail(String msg) {

    }
    @Override
    public void deletePostSuccess(String msg) {

    }
}
