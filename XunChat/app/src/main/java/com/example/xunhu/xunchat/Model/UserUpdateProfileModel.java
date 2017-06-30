package com.example.xunhu.xunchat.Model;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.AsyTasks.UpdateProfileTask;
import com.example.xunhu.xunchat.Model.Interfaces.CRUDUpdateProfileOptions;
import com.example.xunhu.xunchat.Presenter.Interfaces.UpdateProfileActionStatus;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xunhu on 6/14/2017.
 */

public class UserUpdateProfileModel implements CRUDUpdateProfileOptions{
    String url = MainActivity.domain_url+"edit_profile.php";
    UpdateProfileActionStatus updateProfileActionStatus;
    Context context;
    public UserUpdateProfileModel(UpdateProfileActionStatus updateProfileActionStatus, Context context){
        this.updateProfileActionStatus=updateProfileActionStatus;
        this.context=context;
    }

    @Override
    public void updateCheck(final String username, final String title, final String content) {
        new UpdateProfileTask(updateProfileActionStatus).execute(username,title,content);

   }
}
