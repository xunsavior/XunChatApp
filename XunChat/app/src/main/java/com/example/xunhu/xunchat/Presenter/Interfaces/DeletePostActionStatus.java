package com.example.xunhu.xunchat.Presenter.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Post;

/**
 * Created by xunhu on 8/21/2017.
 */

public interface DeletePostActionStatus {
    void deletePostFail(String msg);
    void deletePostSuccess(Post post);
}
