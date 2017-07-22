package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.Entities.Message;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Model.Services.XunChatReceiveMessageService;
import com.example.xunhu.xunchat.Presenter.SendMessagePresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.ChatMessageAdapter;
import com.example.xunhu.xunchat.View.Interfaces.SendChatView;
import com.example.xunhu.xunchat.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xunhu on 7/18/2017.
 */

public class ChatBoardActivity extends Activity implements SendChatView {
    @BindView(R.id.iv_chat_activity_back) ImageView ivBack;
    @BindView(R.id.tv_remark) TextView tvRemark;
    @BindView(R.id.lv_message) ListView lvMessage;
    @BindView(R.id.ib_add) ImageButton ibAdd;
    @BindView(R.id.et_message) EditText etMessage;
    @BindView(R.id.ib_emoji) ImageButton ibEmoji;
    @BindView(R.id.ib_voice) ImageButton ibVoice;
    @BindView(R.id.ib_sending) ImageButton ibSending;
    private User user;
    List<Message> messages = new ArrayList<>();
    ChatMessageAdapter adapter;
    SendMessagePresenter presenter;
    IntentFilter intentFilter = new IntentFilter();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadMessage();

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_layout);
        ButterKnife.bind(this);
        adapter = new ChatMessageAdapter(this,R.layout.message_unit_layout,messages);
        lvMessage.setAdapter(adapter);
        user = (User) getIntent().getSerializableExtra("user");
        tvRemark.setText(user.getRemark());
    }
    @OnClick({R.id.iv_chat_activity_back,R.id.ib_sending})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.iv_chat_activity_back:
                onBackPressed();
                break;
            case R.id.ib_sending:
                sendMessageAndStoreMessage();
                break;
            default:
                break;
        }
    }
    public void sendMessageAndStoreMessage(){
        if (etMessage.getText().toString().isEmpty()){
            etMessage.setError("empty text");
        }else {
            long timestamp = System.currentTimeMillis();
            presenter = new SendMessagePresenter(this);
            presenter.sendingMessage(MainActivity.me,user.getUsername(),user.getUserID(),
                    0,etMessage.getText().toString(),timestamp);
            Message message = new Message(MainActivity.domain_url+MainActivity.me.getUrl(),
                    0,0,etMessage.getText().toString(),String.valueOf(timestamp));
            messages.add(message);
            adapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
            storeLatestMessage(user.getUserID(),user.getUsername(),user.getRemark(),user.getUrl(),
                    etMessage.getText().toString(),String.valueOf(timestamp),0);
            etMessage.setText("");
        }
    }
    public void clearUnreadMessage(){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        Long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("unread",0);
        database.update("latest_message",contentValues,
                "username=? and friend_username=?",new String[]{MainActivity.me.getUsername(),user.getUsername()});
    }

    public void storeLatestMessage(int friendID, String friendUsername,String friendNickname,
                                   String friendURL,String message,String timestamp,int messageType ){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        String queryFriendName = "";
        Cursor cursor = database.rawQuery("select friend_username from latest_message where username=? " +
                "and friend_username=?",new String[]{MainActivity.me.getUsername(),friendUsername});
            if (cursor.moveToFirst()){
                do {
                    queryFriendName = cursor.getString(cursor.getColumnIndex("friend_username"));
                }while (cursor.moveToNext());
                cursor.close();
            }
        System.out.println("@ query "+queryFriendName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("friend_id",friendID);
        contentValues.put("friend_username",friendUsername);
        contentValues.put("friend_nickname",friendNickname);
        contentValues.put("friend_url",friendURL);
        contentValues.put("friend_latest_message",message);
        contentValues.put("friend_time",timestamp);
        contentValues.put("type",messageType);
        contentValues.put("unread",0);
        contentValues.put("username",MainActivity.me.getUsername());
        if (queryFriendName.isEmpty()){
            database.insert("latest_message",null,contentValues);
        }else {
            database.update("latest_message",
                    contentValues,
                    "username=? and friend_username=?",
                    new String[]{MainActivity.me.getUsername(),friendUsername});
        }
        contentValues.clear();
        contentValues.put("friend_username",friendUsername);
        contentValues.put("username",MainActivity.me.getUsername());
        contentValues.put("message_type",messageType);
        contentValues.put("me_or_friend",0);
        contentValues.put("message_content",message);
        contentValues.put("time",timestamp);
        contentValues.put("is_sent",1);
        database.insert("message",null,contentValues);
    }
    public void loadMessage(){
        messages.clear();
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from message where username=? and friend_username=? order by time",
                new String[]{MainActivity.me.getUsername(),user.getUsername()});
        if (cursor.moveToFirst()){
            do {
                Message message;
                int messageType = cursor.getInt(cursor.getColumnIndex("message_type"));
                int meOrFriend = cursor.getInt(cursor.getColumnIndex("me_or_friend"));
                String messageContent = cursor.getString(cursor.getColumnIndex("message_content"));
                String timestamp = cursor.getString(cursor.getColumnIndex("time"));
                int isSent = cursor.getInt(cursor.getColumnIndex("is_sent"));
                message = (meOrFriend==0) ?
                        new Message(MainActivity.domain_url+MainActivity.me.getUrl(),messageType,meOrFriend,messageContent,timestamp)
                        :new Message(MainActivity.domain_url+user.getUrl(),messageType,meOrFriend,messageContent,timestamp);
                message.setIsSentSuccess(isSent);
                messages.add(message);
            }while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
    }
    @Override
    public void sendingMessageFail(long timestamp,String msg) {

        for (int i=0;i<messages.size();i++){
            if (messages.get(i).getTime().equals(String.valueOf(timestamp))){
                messages.get(i).setIsSentSuccess(0);
                setMessageFalse(MainActivity.me.getUsername(),user.getUsername(),String.valueOf(timestamp));
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void setMessageFalse(String myUsername,String friendUsername,String time){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_sent",0);
        database.update("message",contentValues,"(username=?) and (friend_username=?) and (time=?)",
                new String[]{myUsername,friendUsername,time});
    }

    @Override
    public void sendingMessageSuccessful(long timestamp) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        intentFilter.addAction(XunChatReceiveMessageService.REFRESH_CHAT_BOARD);
        registerReceiver(broadcastReceiver,intentFilter);
        loadMessage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearUnreadMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void scrollMyListViewToBottom() {
        lvMessage.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lvMessage.setSelection(adapter.getCount() - 1);
            }
        });
    }
}
