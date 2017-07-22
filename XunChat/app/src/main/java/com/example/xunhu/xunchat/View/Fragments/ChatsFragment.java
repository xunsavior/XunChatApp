package com.example.xunhu.xunchat.View.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xunhu.xunchat.Model.Entities.LatestMessage;
import com.example.xunhu.xunchat.Model.Services.XunChatReceiveMessageService;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.LatestChatAdapter;
import com.example.xunhu.xunchat.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xunhu on 6/7/2017.
 */

public class ChatsFragment extends Fragment {
    @BindView(R.id.lvLatestMessages)
    ListView lvMessages;
    private Unbinder unbinder;
    private IntentFilter filter;
    LatestChatAdapter adapter;
    List<LatestMessage> latestMessages = new ArrayList<>();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case XunChatReceiveMessageService.REFRESH_CHAT_FRAGMENT:
                    loadUnreadChat();
                    break;
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_fragment_layout,container,false);
        unbinder= ButterKnife.bind(this,view);
        adapter = new LatestChatAdapter(getContext(),R.layout.latest_chat_unit_layout,latestMessages);
        lvMessages.setAdapter(adapter);
        filter = new IntentFilter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        filter.addAction(XunChatReceiveMessageService.REFRESH_CHAT_FRAGMENT);
        getContext().registerReceiver(receiver,filter);
        if (MainActivity.me!=null){
            loadUnreadChat();
        }
    }

    public void loadUnreadChat(){
        latestMessages.clear();
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from latest_message where username=? order by friend_time DESC",
                new String[]{MainActivity.me.getUsername()});
        if (cursor.moveToFirst()){
            do {
                int friendID = cursor.getInt(cursor.getColumnIndex("friend_id"));
                String friendUsername = cursor.getString(cursor.getColumnIndex("friend_username"));
                String friendNickname = cursor.getString(cursor.getColumnIndex("friend_nickname"));
                String friendURL = cursor.getString(cursor.getColumnIndex("friend_url"));
                String message = cursor.getString(cursor.getColumnIndex("friend_latest_message"));
                String timestamp = cursor.getString(cursor.getColumnIndex("friend_time"));
                int messageType = cursor.getInt(cursor.getColumnIndex("type"));
                int unread = cursor.getInt(cursor.getColumnIndex("unread"));
                LatestMessage latestMessage = new LatestMessage(friendID,friendUsername,
                        friendNickname,friendURL,message,timestamp,messageType,unread);
                latestMessages.add(latestMessage);
            }while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(receiver);
    }
}
