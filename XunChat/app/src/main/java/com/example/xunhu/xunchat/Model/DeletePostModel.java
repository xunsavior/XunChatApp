package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.DeleteFriendTask;
import com.example.xunhu.xunchat.Model.AsyTasks.DeletePostTask;
import com.example.xunhu.xunchat.Model.Entities.Post;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDDeletePostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.DeletePostActionStatus;

/**
 * Created by xunhu on 8/21/2017.
 */

public class DeletePostModel implements CRUDDeletePostOptions{
    DeletePostActionStatus deletePostActionStatus;
    public DeletePostModel(DeletePostActionStatus deletePostActionStatus){
        this.deletePostActionStatus=deletePostActionStatus;
    }

    @Override
    public void performDeletePost(Post post) {
        new DeletePostTask(deletePostActionStatus).execute(post);
    }
}
