package com.example.xunhu.xunchat.View.Fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.Entities.Friend;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.AllAdapters.SingleContactAdapter;
import com.example.xunhu.xunchat.View.MainActivity;

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

public class ContactsFragment extends Fragment {
    @BindView(R.id.rlNewFriends) RelativeLayout rlNewFriends;
    @BindView(R.id.tv_num_of_request) TextView tvNumOfRequests;
    @BindView(R.id.lv_contacts) ListView lvContacts;
    private Unbinder unbinder;
    ContactFragmentInterface comm;
    IntentFilter intentFilter = new IntentFilter();
    List<Friend> friends = new ArrayList<>();
    SingleContactAdapter adapter;
    public static final String NEW_FRIEND_ADDED = "new friend added";

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case NEW_FRIEND_ADDED:
                    System.out.println("@ roger that");
                    break;
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment_layout,container,false);
        unbinder=ButterKnife.bind(this,view);
        adapter= new SingleContactAdapter(getContext(),R.layout.single_contact_unit_layout,friends);
        lvContacts.setAdapter(adapter);
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (ContactFragmentInterface) activity;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick({R.id.rlNewFriends})
    public void onRespond(View view){
        switch (view.getId()){
            case R.id.rlNewFriends:
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("type","new friends");
                startActivity(intent);
                comm.clearRequests();
                break;
            default:
                break;
        }
    }
    @OnTouch({R.id.rlNewFriends})
    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                rlNewFriends.setBackgroundColor(Color.parseColor("#E1E0DE"));
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
    public interface ContactFragmentInterface{
        void clearRequests();
    }
    @Override
    public void onResume() {
        super.onResume();
        friends.clear();
        intentFilter.addAction(NEW_FRIEND_ADDED);
        getContext().registerReceiver(broadcastReceiver,intentFilter);
        loadFriendList();
    }
    public void loadFriendList(){
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
        friends.clear();
    }
}
