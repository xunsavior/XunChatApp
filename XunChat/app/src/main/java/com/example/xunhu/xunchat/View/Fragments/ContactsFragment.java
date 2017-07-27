package com.example.xunhu.xunchat.View.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.Friend;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.Presenter.RetrieveFriendListPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.Activities.SubActivity_;
import com.example.xunhu.xunchat.View.AllAdapters.SingleContactAdapter;
import com.example.xunhu.xunchat.View.Interfaces.RetrieveFriendListView;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import com.example.xunhu.xunchat.View.MainActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by xunhu on 6/7/2017.
 */
@EFragment(R.layout.contacts_fragment_layout)
public class ContactsFragment extends Fragment implements RetrieveFriendListView {
    @ViewById(R.id.rlNewFriends) RelativeLayout rlNewFriends;
    @ViewById(R.id.tv_num_of_request) TextView tvNumOfRequests;
    @ViewById(R.id.lv_contacts) ListView lvContacts;
    ContactFragmentInterface comm;
    IntentFilter intentFilter = new IntentFilter();
    List<Friend> friends = new ArrayList<>();
    SingleContactAdapter adapter;
    public static final String NEW_FRIEND_ADDED = "new friend added";
    public static final String REFRESH_FRIEND_LIST="fresh friend list";
    RetrieveFriendListPresenter retrieveFriendListPresenter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case NEW_FRIEND_ADDED:
                    break;
                case REFRESH_FRIEND_LIST:
                    loadFriendList();
                    break;
                default:
                    break;
            }
        }
    };
    @AfterViews void setContactFragmentViews(){
        adapter= new SingleContactAdapter(getContext(),R.layout.single_contact_unit_layout,friends);
        lvContacts.setAdapter(adapter);
        retrieveFriendList();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (ContactFragmentInterface) activity;
    }
    @Click({R.id.rlNewFriends,R.id.rlNewFriends}) void onRespond(View view){
        switch (view.getId()){
            case R.id.rlNewFriends:
                SubActivity_.intent(getContext()).extra("type","new friends").start();
                comm.clearRequests();
                break;
            default:
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    @Touch({R.id.rlNewFriends}) boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                rlNewFriends.setBackgroundColor(R.color.SkyBlue);
                break;
            case MotionEvent.ACTION_CANCEL:
                rlNewFriends.setBackgroundColor(Color.WHITE);
                break;
            case MotionEvent.ACTION_UP:
                rlNewFriends.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }
        return false;
    }
    public TextView getTvNumOfRequests() {
        return tvNumOfRequests;
    }

    @Override
    public void onRetrieveFriendListSuccessful(String msg) {
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        database.delete("friend","username=?",new String[]{MainActivity.me.getUsername()});
        try {
            JSONArray jsonArray = new JSONArray(msg);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                int friendID = object.getInt("friend_id");
                String friendUsername = object.getString("friend_username");
                String friendNickname = object.getString("friend_nickname");
                String friendURL = object.getString("friend_url");
                ContentValues values = new ContentValues();
                values.put("friend_id",friendID);
                values.put("friend_username",friendUsername);
                values.put("friend_nickname",friendNickname);
                values.put("friend_url",MainActivity.domain_url+friendURL);
                values.put("username",MainActivity.me.getUsername());
                database.insert("friend",null,values);
            }
            Intent intent = new Intent(REFRESH_FRIEND_LIST);
            getContext().sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRetrieveFriendListFail(String msg) {
        if (msg.equals("you currently have no friend!")){
            SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
            database.delete("friend","username=?",new String[]{MainActivity.me.getUsername()});
            friends.clear();
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public interface ContactFragmentInterface{
        void clearRequests();
    }
    @Override
    public void onResume() {
        super.onResume();
        intentFilter.addAction(NEW_FRIEND_ADDED);
        intentFilter.addAction(REFRESH_FRIEND_LIST);
        getContext().registerReceiver(broadcastReceiver,intentFilter);
        if (MainActivity.me!=null){
            loadFriendList();
        }
    }
    public void loadFriendList(){
        friends.clear();
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from friend where username=?",new String[]{MainActivity.me.getUsername()});
        if (cursor.moveToFirst()){
            do {
                int friendID = cursor.getInt(cursor.getColumnIndex("friend_id"));
                String friendUsername = cursor.getString(cursor.getColumnIndex("friend_username"));
                String friendNickname = cursor.getString(cursor.getColumnIndex("friend_nickname"));
                String friendURL = cursor.getString(cursor.getColumnIndex("friend_url"));
                boolean FirstOrNot =false;
                Friend friend = new Friend(friendID,friendUsername,friendNickname,friendURL,FirstOrNot);
                friends.add(friend);
            }while (cursor.moveToNext());
        }
        cursor.close();
        Collections.sort(friends, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                return o1.getNickname().toLowerCase().compareTo(o2.getNickname().toLowerCase());
            }
        });
        for (int i=0;i<friends.size();i++){
                if (i==0){
                       friends.get(i).setFirstOrNot(true);
                }else {
                    if (friends.get(i).getNickname().substring(0,1).toLowerCase().
                            equals(friends.get(i-1).getNickname().
                                    substring(0,1).toLowerCase())){
                        friends.get(i).setFirstOrNot(false);
                    }else {
                        friends.get(i).setFirstOrNot(true);
                    }
                }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
    }
    public void retrieveFriendList(){
        if (MainActivity.me!=null){
            retrieveFriendListPresenter = new RetrieveFriendListPresenter(this);
            retrieveFriendListPresenter.retrieveFriendList(MainActivity.me.getId());
        }
    }
}
