package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.FetchPostTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDLoadPostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.LoadPostActionStatus;

/**
 * Created by xunhu on 8/18/2017.
 */

public class MyLoadPostModel implements CRUDLoadPostOptions {
    LoadPostActionStatus loadPostActionStatus;
    public MyLoadPostModel(LoadPostActionStatus loadPostActionStatus){
        this.loadPostActionStatus=loadPostActionStatus;
    }
    @Override
    public void loadPost(int id) {
        new FetchPostTask(loadPostActionStatus).execute(id);
    }
}
