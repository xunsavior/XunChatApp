package com.example.xunhu.xunchat.View.Interfaces;
import com.example.xunhu.xunchat.Model.Entities.Post;

/**
 * Created by xunhu on 8/21/2017.
 */

public interface DeletePostView {
    void deleteSuccess(Post post);
    void deleteFail(String msg);
}
