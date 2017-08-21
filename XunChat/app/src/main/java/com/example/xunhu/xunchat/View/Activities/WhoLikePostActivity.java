package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.WhoLikePost;
import com.example.xunhu.xunchat.Presenter.LoadWhoLikePostPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.WhoLikePostAdapter;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.LoadingWhoLikePostView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 8/21/2017.
 */
@EActivity(R.layout.who_likes_activity)
public class WhoLikePostActivity extends Activity implements LoadingWhoLikePostView {
    @ViewById
    ListView lvWhoLikePost;
    LoadWhoLikePostPresenter loadWhoLikePostPresenter;
    MyDialog myDialog;
    List<WhoLikePost> whoLikePosts = new ArrayList<>();
    WhoLikePostAdapter whoLikePostAdapter;
    int postID = -1;
    @AfterViews void setWhoLikePostActivityViews(){
        myDialog = new MyDialog(this);
        myDialog.createDeletePostDialog();
        whoLikePostAdapter = new WhoLikePostAdapter(this,R.layout.who_likes_activity,whoLikePosts);
        lvWhoLikePost.setAdapter(whoLikePostAdapter);
        postID=getIntent().getIntExtra("post_id",-1);
        loadWhoLikePostPresenter = new LoadWhoLikePostPresenter(this);
        loadWhoLikePostPresenter.loadWhoLikePost(postID);
    }
    @Override
    public void loadingFail(String msg) {
        myDialog.cancelDeletePostDialog();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingPost(String msg) {
        myDialog.cancelDeletePostDialog();
        try {
            JSONArray jsonArray = new JSONArray(msg);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                int userID = object.getInt("user_id");
                String username = object.getString("username");
                String nickname = object.getString("nickname");
                String url = object.getString("url");
                long timestamp = object.getLong("timestamp");
               // String timestamp = object.getString("timestamp");
                WhoLikePost whoLikePost = new WhoLikePost(userID,username,nickname,url,String.valueOf(timestamp));
                whoLikePosts.add(whoLikePost);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        whoLikePostAdapter.notifyDataSetChanged();
    }
}
