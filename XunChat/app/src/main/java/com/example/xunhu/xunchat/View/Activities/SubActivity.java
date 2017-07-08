package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.FriendRequestAdapter;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 6/19/2017.
 */

public class SubActivity extends Activity implements SearchFriendInterface {
    public static Me me = MainActivity.me;
    MyDialog myDialog;
    private ImageView ivBackImage;
    private ImageView ivCreatePost;
    ImageView ivNewRequestBack;
    ListView lvNewRequests;
    private EditText etSearchFriends;
    List<User> users;
    MySearchFriendPresenter presenter;

    private static final String NEW_FRIEND = "new friends";
    private static final String MOMENT = "moments";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDialog = new MyDialog(this);
        String viewType = getIntent().getStringExtra("type");
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
    private void createMomentView(){
        setContentView(R.layout.moments_display_layout);
        ivBackImage = (ImageView) findViewById(R.id.iv_moment_back);
        ivCreatePost = (ImageView) findViewById(R.id.iv_post_photo);
        ivCreatePost.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(SubActivity.this,EditMomentActivity.class);
                intent.putExtra("image",false);
                startActivity(intent);
                return false;
            }
        });
        ivBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void createNewFriendsView(){
        setContentView(R.layout.new_friends_layout);
        presenter = new MySearchFriendPresenter(this);
        ivNewRequestBack = (ImageView) findViewById(R.id.iv_new_friends_back);
        lvNewRequests = (ListView) findViewById(R.id.lv_new_requests);
        etSearchFriends = (EditText) findViewById(R.id.et_search_username);
        List<Request> list = new ArrayList<>();
        FriendRequestAdapter adapter = new FriendRequestAdapter(this,R.layout.friend_request_unit,list);
        ivNewRequestBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isRead","1");
        Cursor cursor = database.rawQuery("select sender_id,sender,isRead,extras,isAgreed,time," +
                        "url,username" +
                        " from request where username=? order by time DESC",
                new String[]{MainActivity.me.getUsername()});
        if (cursor.moveToFirst()){
            do {
                int senderID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sender_id")));
                String senderName = cursor.getString(cursor.getColumnIndex("sender"));
                String extras = cursor.getString(cursor.getColumnIndex("extras"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String isAgreed = cursor.getString(cursor.getColumnIndex("isAgreed"));
                String isRead = cursor.getString(cursor.getColumnIndex("isRead"));
                Request request = new Request(senderID,senderName,extras,isAgreed,url,isRead);
                list.add(request);
            }while (cursor.moveToNext());
        }
        lvNewRequests.setAdapter(adapter);
        //update is read
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
              User user = new User(user_id,username,nickname,url,gender,region,whatsup,age,relationshipType);
              Intent intent = new Intent(SubActivity.this,ProfileActivity.class);
              intent.putExtra("user",user);
              startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
