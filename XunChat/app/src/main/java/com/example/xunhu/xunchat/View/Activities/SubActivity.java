package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.Post;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.LoadPostPresenter;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.Presenter.ScrollLoadingPostPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.FriendRequestAdapter;
import com.example.xunhu.xunchat.View.AllAdapters.SingePostAdapter;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.LoadPostView;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import com.example.xunhu.xunchat.View.Interfaces.scrollLoadingPostView;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 6/19/2017.
 */
@EActivity
public class SubActivity extends Activity implements SearchFriendInterface,LoadPostView,
        scrollLoadingPostView,SingePostAdapter.SinglePostAdapterInterface {
    public static Me me = MainActivity.me;
    @ViewById(R.id.iv_moment_back) ImageView ivBackImage;
    @ViewById(R.id.iv_post_photo) ImageView ivCreatePost;
    @ViewById(R.id.iv_new_friends_back) ImageView ivNewRequestBack;
    @ViewById(R.id.lv_new_requests) ListView lvNewRequests;
    @ViewById(R.id.et_search_username) EditText etSearchFriends;
    @ViewById(R.id.rvImagePost) RecyclerView rvImagePost;
    @ViewById(R.id.slRefreshPost) SwipeRefreshLayout slRefreshPost;
    @ViewById(R.id.tvLoadingBottomPosts) TextView tvLoadingBottomPosts;
    @ViewById(R.id.ll)
    LinearLayout ll;
    int momentType = -1;
    MySearchFriendPresenter presenter;
    LoadPostPresenter loadPostPresenter;
    ScrollLoadingPostPresenter scrollLoadingPostPresenter;
    MyDialog loadingPostDialog;
    private String viewType = "";
    private static final String NEW_FRIEND = "new friends";
    private static final String MOMENT = "moments";
    List<Request> list = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    FriendRequestAdapter adapter;
    MyDialog myDialog;
    SingePostAdapter singePostAdapter;
    LoadPostView loadPostView = this;
    scrollLoadingPostView scrollLoadingPostView = this;
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(this);
        viewType = getIntent().getStringExtra("type");
    }

    @Override
    protected void onStart() {
        super.onStart();
        determineLayout(viewType);
    }
    private void determineLayout(String viewType){
        switch (viewType){
            case MOMENT:
                createMomentView();
                break;
            case NEW_FRIEND:
                createNewFriendsView();
                break;
            default:
                break;
        }
    }
    @Click({R.id.iv_moment_back,R.id.iv_new_friends_back,R.id.iv_post_photo})
    void onSubActivityClickRespond(View view){
        switch (view.getId()){
            case R.id.iv_moment_back:
                onBackPressed();
                break;
            case R.id.iv_new_friends_back:
                onBackPressed();
                break;
            case R.id.iv_post_photo:
                createCameraPopupMenu(view);
                break;
            default:
                break;
        }
    }
    @LongClick({R.id.iv_post_photo})
    boolean onSubActivityLongClickRespond(View view){
        switch (view.getId()){
            case R.id.iv_post_photo:
                EditMomentActivity_.intent(this).extra("image",false).start();
                break;
        }
        return false;
    }
    private void createMomentView(){
        setContentView(R.layout.moments_display_layout);
        final int id = getIntent().getIntExtra("id",-1);
        momentType = getIntent().getIntExtra("moment_type",-1);
        loadingPostDialog = new MyDialog(this);
        loadingPostDialog.createLoadingGifDialog();
        singePostAdapter = new SingePostAdapter(this,posts,this);
        slRefreshPost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (momentType==100){
                    scrollLoadingPostPresenter = new ScrollLoadingPostPresenter(scrollLoadingPostView);
                    scrollLoadingPostPresenter.operateScrollLoadingPost(id,String.valueOf(System.currentTimeMillis()),100);
                }else {
                    loadPostPresenter  = new LoadPostPresenter(loadPostView);
                    loadPostPresenter.loadPosts(id);
                }

            }
        });
        slRefreshPost.setColorSchemeResources(R.color.SkyBlue);
        slRefreshPost.setProgressBackgroundColorSchemeResource(R.color.white);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvImagePost.setLayoutManager(linearLayoutManager);
        rvImagePost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0){
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            ll.setVisibility(View.VISIBLE);
                            tvLoadingBottomPosts.setText("Loading...");
                            if(loading){
                                loading = false;
                                if (momentType==100){
                                    scrollLoadingPostPresenter = new ScrollLoadingPostPresenter(scrollLoadingPostView);
                                    scrollLoadingPostPresenter.operateScrollLoadingPost(id,posts.get(posts.size()-1).getTimestamp(),101);
                                }else {
                                    scrollLoadingPostPresenter = new ScrollLoadingPostPresenter(scrollLoadingPostView);
                                    scrollLoadingPostPresenter.operateScrollLoadingPost(id,posts.get(posts.size()-1).getTimestamp(),1);
                                }
                            }
                        }
                }else {
                    ll.setVisibility(View.GONE);
                }

            }
        });
        rvImagePost.setHasFixedSize(true);
        rvImagePost.setAdapter(singePostAdapter);
        if (id!=-1){
            if (momentType==100){
                scrollLoadingPostPresenter = new ScrollLoadingPostPresenter(scrollLoadingPostView);
                scrollLoadingPostPresenter.operateScrollLoadingPost(id,String.valueOf(System.currentTimeMillis()),100);
            }else {
                loadPostPresenter  = new LoadPostPresenter(loadPostView);
                loadPostPresenter.loadPosts(id);
            }
        }
    }
    void createCameraPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.take_photo:
                        return true;
                    case R.id.photo_gallery:
                        PhotoGalleryActivity_.intent(SubActivity.this).start();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.photo_selection_menu);
        popupMenu.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewType.equals(NEW_FRIEND)){
            loadFriendRequest();
        }
    }
    private void createNewFriendsView(){
        setContentView(R.layout.new_friends_layout);
        presenter = new MySearchFriendPresenter(this);
        adapter = new FriendRequestAdapter(this,R.layout.friend_request_unit,list);
        etSearchFriends.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etSearchFriends.getWindowToken(), 0);
                    if (!etSearchFriends.getText().toString().isEmpty()){
                        myDialog.createLoadingGifDialog();
                        presenter.attemptSearchFriends(etSearchFriends.getText().toString());
                    }else {
                        etSearchFriends.setError("Please enter the username you are searching for!");
                    }
                    return true;
                }
                return false;
            }
        });
    }
    public void loadFriendRequest(){
        list.clear();
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isRead","1");
        Cursor cursor = database.rawQuery("select sender_id,sender,sender_nickname,isRead,extras,isAgreed,time," +
                        "url,username" +
                        " from request where username=? order by time DESC",
                new String[]{MainActivity.me.getUsername()});
        if (cursor.moveToFirst()){
            do {
                int senderID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sender_id")));
                String senderName = cursor.getString(cursor.getColumnIndex("sender"));
                String senderNickname = cursor.getString(cursor.getColumnIndex("sender_nickname"));
                String extras = cursor.getString(cursor.getColumnIndex("extras"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String isAgreed = cursor.getString(cursor.getColumnIndex("isAgreed"));
                String isRead = cursor.getString(cursor.getColumnIndex("isRead"));
                Request request = new Request(senderID,senderName,senderNickname,extras,isAgreed,url,isRead);
                list.add(request);
            }while (cursor.moveToNext());
        }
        lvNewRequests.setAdapter(adapter);
        database.update("request",contentValues,"username=? AND isRead=?",new String[]{MainActivity.me.getUsername(),"0"});
        cursor.close();
    }
    @Override
    public void searchFriendFail(String msg) {
        myDialog.cancelLoadingGifDialog();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadResults(String msg) {
        myDialog.cancelLoadingGifDialog();
        try {
              JSONObject object = new JSONObject(msg);
              int user_id = object.getInt("user_id");
              String username = object.getString("username");
              String nickname = object.getString("nickname");
              String whatsup = object.getString("what_is_up");
              String url = object.getString("url");
              int age = object.getInt("age");
              String gender = object.getString("gender");
              String region = object.getString("region");
              int relationshipType = object.getInt("relationship_type");
              String images = object.getString("images");
              User user = new User(user_id,username,nickname,url,gender,region,whatsup,age,relationshipType);
              user.setImages(images);
              ProfileActivity_.intent(this).extra("user",user).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void loadingPostSuccess(String msg) {
        Post post = null;
        loadingPostDialog.cancelLoadingGifDialog();
        posts.clear();
        slRefreshPost.setRefreshing(false);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                int postID = jsonObject.getInt("post_id");
                String nickname = (momentType==100)? jsonObject.getString("nickname"):getIntent().getStringExtra("nickname");
                String imageURL = jsonObject.getString("image_url");
                String postContent = jsonObject.getString("post_content");
                String caption = jsonObject.getString("caption");
                int postType = jsonObject.getInt("post_type");
                String timestamp = jsonObject.getString("timestamp");
                String location = jsonObject.getString("location");
                int numOfLikes = jsonObject.getInt("num_of_likes");
                int posterID = jsonObject.getInt("poster_id");
                post = new Post(postID,posterID,nickname,imageURL,postContent,caption,postType,timestamp,location);
                post.setNumberLikes(numOfLikes);
                int likedPostID = jsonObject.getInt("liked_post_id");
                post.setIsLiked(1);
                posts.add(post);
            } catch (JSONException e) {
                e.printStackTrace();
                if (post!=null){
                    post.setIsLiked(0);
                    posts.add(post);
                }
            }
        }
        singePostAdapter.notifyDataSetChanged();
    }
    @Override
    public void loadingPostFail(String msg) {
        loadingPostDialog.cancelLoadingGifDialog();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void scrollLoadingSuccess(String msg, int type) {
        Post post = null;
        switch (type){
            case 1:
                break;
            case 100:
                posts.clear();
                loadingPostDialog.cancelLoadingGifDialog();
                slRefreshPost.setRefreshing(false);
                break;
        }
        try {
            JSONArray jsonArray = new JSONArray(msg);
            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int postID = jsonObject.getInt("post_id");
                    String nickname = (momentType==100)? jsonObject.getString("nickname"):getIntent().getStringExtra("nickname");
                    String imageURL = jsonObject.getString("image_url");
                    String postContent = jsonObject.getString("post_content");
                    String caption = jsonObject.getString("caption");
                    int postType = jsonObject.getInt("post_type");
                    String timestamp = jsonObject.getString("timestamp");
                    String location = jsonObject.getString("location");
                    int numOfLikes = jsonObject.getInt("num_of_likes");
                    int posterID = jsonObject.getInt("poster_id");
                    post = new Post(postID,posterID,nickname,imageURL,postContent,caption,postType,timestamp,location);
                    post.setNumberLikes(numOfLikes);
                    int likedPostID = jsonObject.getInt("liked_post_id");
                    post.setIsLiked(1);
                    posts.add(post);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (post!=null){
                        post.setIsLiked(0);
                        posts.add(post);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        singePostAdapter.notifyDataSetChanged();
        ll.setVisibility(View.GONE);
        loading=true;
    }
    @Override
    public void scrollLoadingFail(String msg) {
        loadingPostDialog.cancelLoadingGifDialog();
        if (msg.equals("the user has not yet posted anything!")){
            tvLoadingBottomPosts.setText(":) I have a bottom line!");
        }else {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
        loading=true;
    }
    @Override
    public void addLike(int postID) {
        for (int i=0;i<posts.size();i++){
            if (posts.get(i).getPostID()==postID) {
                posts.get(i).setIsLiked(1);
                posts.get(i).setNumberLikes(posts.get(i).getNumberLikes()+1);
                break;
            }
        }
        singePostAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelLike(int postID) {
        for (int i=0;i<posts.size();i++){
            if (posts.get(i).getPostID()==postID) {
                posts.get(i).setIsLiked(0);
                posts.get(i).setNumberLikes(posts.get(i).getNumberLikes()-1);
                break;
            }
        }
        singePostAdapter.notifyDataSetChanged();
    }

    @Override
    public void deletePost(Post post) {
        posts.remove(post);
        singePostAdapter.notifyDataSetChanged();
    }
}
