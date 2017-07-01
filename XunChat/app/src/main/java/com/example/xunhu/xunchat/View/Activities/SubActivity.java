package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.FriendRequestAdapter;
import com.example.xunhu.xunchat.View.AllAdapters.SearchFriendsAdapter;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunhu on 6/19/2017.
 */

public class SubActivity extends Activity implements SearchFriendInterface {
    public static Me me = MainActivity.me;

    private ImageView ivBackImage;
    private ImageView ivCreatePost;
    @BindView(R.id.iv_new_friends_back)  ImageView ivNewRequestBack;
    @BindView(R.id.lv_new_requests)  ListView lvNewRequests;
    private ImageView ivSearchFriend;
    private ListView lvSearchFriendsResults;
    private EditText etSearchFriends;
    List<User> users;
    SearchFriendsAdapter adapter;
    MySearchFriendPresenter presenter;

    private static final String NEW_FRIEND = "new friends";
    private static final String MOMENT = "moments";
    private static final String SEARCHING_FRIENDS = "searching friends";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            case SEARCHING_FRIENDS:
                createSearchingFriends();
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
        ButterKnife.bind(this);
        List<Request> list = new ArrayList<>();
        FriendRequestAdapter adapter = new FriendRequestAdapter(this,R.layout.friend_request_unit,list);
        lvNewRequests.setAdapter(adapter);
        ivNewRequestBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isRead","true");
        Cursor cursor = database.rawQuery("select sender,extras,url,isAgreed,isRead,time from request where username=? order by time DESC",
                new String[]{MainActivity.me.getUsername()});
        if (cursor.moveToFirst()){
            do {
                String sender = cursor.getString(cursor.getColumnIndex("sender"));
                String extras = cursor.getString(cursor.getColumnIndex("extras"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String isAgreed = cursor.getString(cursor.getColumnIndex("isAgreed"));
                String isRead = cursor.getString(cursor.getColumnIndex("isRead"));
                Request request = new Request(sender,extras,isAgreed,url,isRead);
                list.add(request);
                adapter.notifyDataSetChanged();
            }while (cursor.moveToNext());
        }
        //update is read
        database.update("request",contentValues,"username=? AND isRead=?",new String[]{MainActivity.me.getUsername(),"false"});
        cursor.close();
    }
    private void createSearchingFriends(){
        setContentView(R.layout.search_friends_layout);
        presenter = new MySearchFriendPresenter(this);
        users= new ArrayList<>();
        adapter = new SearchFriendsAdapter(this,R.layout.single_contact_unit_layout,users);
        ivSearchFriend = (ImageView) findViewById(R.id.iv_search_friends);
        lvSearchFriendsResults = (ListView) findViewById(R.id.lv_search_results);
        etSearchFriends = (EditText) findViewById(R.id.et_search_friends);
        lvSearchFriendsResults.setAdapter(adapter);
        lvSearchFriendsResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.WHITE);
                Intent  intent = new Intent(SubActivity.this,ProfileActivity.class);
                intent.putExtra("type","ADD");
                User user = new User(
                        users.get(position).getUsername(),
                        users.get(position).getNickname(),
                        users.get(position).getUrl(),
                        users.get(position).getGender(),
                        users.get(position).getRegion(),
                        users.get(position).getWhatsup(),
                        users.get(position).getAge(),
                        users.get(position).getToken()
                        );
                intent.putExtra("type","Add");
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        ivSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.clear();
                if (etSearchFriends.getText().toString().isEmpty()){
                    etSearchFriends.setError("Please enter the name");
                }else {
                    presenter.attemptSearchFriends(etSearchFriends.getText().toString());
                }
            }
        });
    }

    @Override
    public void searchFriendFail(String msg) {
        Toast.makeText(SubActivity.this,"Unknown error!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadResults(String msg) {
        if (msg.equals("The user does not exist!")){
            Toast.makeText(SubActivity.this,msg,Toast.LENGTH_SHORT).show();
        }else {
            try {
                JSONArray jsonArray = new JSONArray(msg);
                JSONObject object = new JSONObject();
                for (int i=0;i<jsonArray.length();i++){
                    object = jsonArray.getJSONObject(i);
                    User user = new User(
                            object.getString("username"),
                            object.getString("nickname"),
                            object.getString("url"),
                            object.getString("gender"),
                            object.getString("region"),
                            object.getString("what_is_up"),
                            object.getInt("age"),
                            object.getString("token")
                            );
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
