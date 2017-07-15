package com.example.xunhu.xunchat.Model;

import com.example.xunhu.xunchat.Model.AsyTasks.SetRemarkTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDSetRemarkOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.SetRemarkActionStatus;

/**
 * Created by xunhu on 7/14/2017.
 */

public class MySetRemarkModel implements CRUDSetRemarkOptions{
    SetRemarkActionStatus setRemarkActionStatus;

    public MySetRemarkModel(SetRemarkActionStatus setRemarkActionStatus){
        this.setRemarkActionStatus=setRemarkActionStatus;
    }
    @Override
    public void performSetRemark(int myID, int friendID, String remark) {
        new SetRemarkTask(setRemarkActionStatus).execute(String.valueOf(myID),String.valueOf(friendID),remark);
    }
}
