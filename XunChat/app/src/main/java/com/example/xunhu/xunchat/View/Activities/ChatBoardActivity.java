package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.ContentValues;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_layout);
        ButterKnife.bind(this);
        adapter = new ChatMessageAdapter(this,R.layout.message_unit_layout,messages);
        lvMessage.setAdapter(adapter);
        user = (User) getIntent().getSerializableExtra("user");
        tvRemark.setText(user.getRemark());
        clearUnreadMessage();
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
        contentValues.put("friend_time",String.valueOf(timestamp));
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

    }
    @Override
    public void sendingMessageFail(long timestamp) {
        for (int i=0;i<messages.size();i++){
            if (messages.get(i).getTime().equals(String.valueOf(timestamp))){
                messages.get(i).setMessageContent(messages.get(i).getMessageContent()+
                        " "+"(fail to send)");
                storeLatestMessage(user.getUserID(),user.getUsername(),user.getRemark(),user.getUrl(),
                        messages.get(i).getMessageContent(),String.valueOf(timestamp),0);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendingMessageSuccessful(long timestamp) {


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
