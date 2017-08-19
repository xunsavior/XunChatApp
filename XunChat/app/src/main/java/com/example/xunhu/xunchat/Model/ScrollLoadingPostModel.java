package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.ScrollLoadingPostTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDScrollLoadingPostOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.ScrollLoadingPostActionStatus;

/**
 * Created by xunhu on 8/19/2017.
 */

public class ScrollLoadingPostModel implements CRUDScrollLoadingPostOptions {
    ScrollLoadingPostActionStatus scrollLoadingPostActionStatus;

    public ScrollLoadingPostModel(ScrollLoadingPostActionStatus scrollLoadingPostActionStatus){
        this.scrollLoadingPostActionStatus=scrollLoadingPostActionStatus;
    }
    @Override
    public void operateScrollingPostLoading(int id, String timestamp, int type) {
        new ScrollLoadingPostTask(scrollLoadingPostActionStatus).execute(String.valueOf(id),timestamp,String.valueOf(type));
    }
}
