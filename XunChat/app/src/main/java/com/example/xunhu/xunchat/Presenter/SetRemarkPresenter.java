package com.example.xunhu.xunchat.Presenter;

import com.example.xunhu.xunchat.Model.MySetRemarkModel;
import com.example.xunhu.xunchat.Presenter.Interfaces.SetRemarkAction;
import com.example.xunhu.xunchat.Presenter.Interfaces.SetRemarkActionStatus;
import com.example.xunhu.xunchat.View.Interfaces.SetRemarkView;

/**
 * Created by xunhu on 7/14/2017.
 */

public class SetRemarkPresenter implements SetRemarkAction,SetRemarkActionStatus {
    SetRemarkView setRemarkView;
    MySetRemarkModel mySetRemarkModel;
    public SetRemarkPresenter(SetRemarkView setRemarkView){
        this.setRemarkView=setRemarkView;
        mySetRemarkModel = new MySetRemarkModel(this);
    }

    @Override
    public void setRemark(int myID, int friendID, String remark) {
        mySetRemarkModel.performSetRemark(myID,friendID,remark);
    }

    @Override
    public void failToSetRemark(String msg) {
        setRemarkView.setRemarkFail(msg);
    }

    @Override
    public void setRemarkDone(String msg) {
        setRemarkView.setRemarkSuccessful(msg);
    }
}
