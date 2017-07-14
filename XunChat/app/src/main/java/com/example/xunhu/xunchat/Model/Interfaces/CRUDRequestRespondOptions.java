package com.example.xunhu.xunchat.Model.Interfaces;

import com.example.xunhu.xunchat.Model.Entities.Me;

/**
 * Created by xunhu on 7/3/2017.
 */

public interface CRUDRequestRespondOptions {
    void sendRequestRespond(int targetID, Me me,String remark);
}
