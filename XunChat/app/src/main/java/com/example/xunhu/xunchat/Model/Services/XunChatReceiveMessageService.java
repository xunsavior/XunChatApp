package com.example.xunhu.xunchat.Model.Services;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xunhu.xunchat.Model.SQLite.XunChatDatabaseHelper;
import com.example.xunhu.xunchat.View.MainActivity;
import com.example.xunhu.xunchat.View.Notifications.MyNotification;
import com.example.xunhu.xunchat.View.XunApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xunhu on 6/23/2017.
 */

public class XunChatReceiveMessageService extends FirebaseMessagingService {
    private int FRIEND_REQUST_NOTIFICATION_ID = 45612;
    private int REQUEST_RESPOND_NOTIFICATION_ID  = 45613;
    private static final String FRIEND_REQUEST = "friend_request";
    private static final String REQUEST_ACCEPTED = "accepted_respond";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("@ message"+remoteMessage.getData().get("message"));
        try {
            operateMessage(remoteMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void operateMessage(RemoteMessage remoteMessage) throws JSONException {
        JSONObject object = new JSONObject(remoteMessage.getData().get("message"));
        String type = object.getString("message_type");
        switch (type){
            case FRIEND_REQUEST:
                int senderID = object.getInt("sender_id");
                String senderName = object.getString("sender_username");
                String senderNickname = object.getString("sender_nickname");
                String senderURL = object.getString("sender_url");
                String senderExtras = object.getString("sender_extras");
                String requestTime = String.valueOf(System.currentTimeMillis());
                storeFriendRequest(senderID,senderName,senderNickname,senderURL,senderExtras,requestTime);
                sendFriendRequestBroadcast(senderName);
                MyNotification friendRequestNotification = new MyNotification(FRIEND_REQUST_NOTIFICATION_ID);
                String requestTicker = senderName+" has sent you a friend request.";
                String requestContent = " has sent you a friend request.";
                friendRequestNotification.createRequestRespondNotification(senderName,senderURL,requestTicker,requestContent);
                break;
            case REQUEST_ACCEPTED:
                int responderID = object.getInt("responder_id");
                String responderUsername = object.getString("responder_username");
                String responderNickname = object.getString("responder_nickname");
                String responderURL = object.getString("responder_url");
                String respondTime = String.valueOf(System.currentTimeMillis());
                MyNotification respondNotification = new MyNotification(REQUEST_RESPOND_NOTIFICATION_ID);
                String respondTicker = responderUsername+" has accepted your friend request.";
                String respondContent = " has accepted your friend request.";
                storeFriend(responderID,responderUsername,responderNickname,responderURL);
                respondNotification.createRequestRespondNotification(responderUsername,responderURL,respondTicker,respondContent);
                break;
            default:
                break;
        }
    }
    public void storeFriend(int friendID,String friendUsername,String friendNickname,String friendURL){
        XunChatDatabaseHelper xunChatDatabaseHelper = new XunChatDatabaseHelper(XunApplication.getContext(),"XunChat.db",null);
        SQLiteDatabase database = xunChatDatabaseHelper.getWritableDatabase();
        String currentUser = returnCurrentUser();
        if (!currentUser.isEmpty()){
            ContentValues values = new ContentValues();
            values.put("friend_id",friendID);
            values.put("friend_username",friendUsername);
            values.put("friend_nickname",friendNickname);
            values.put("friend_url",friendURL);
            values.put("username",currentUser);
            database.insert("friend",null,values);
        }
    }
    public void sendFriendRequestBroadcast(String username){
        Intent intent = new Intent(FRIEND_REQUEST);
        intent.putExtra("username",username);
        sendBroadcast(intent);
    }
    public String returnCurrentUser(){
        String me="";
        XunChatDatabaseHelper xunChatDatabaseHelper = new XunChatDatabaseHelper(XunApplication.getContext(),"XunChat.db",null);
        SQLiteDatabase database = xunChatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT username FROM user WHERE isActive=?",new String[]{"1"} );
        if (cursor.moveToFirst()){
            do{
                me  = cursor.getString(cursor.getColumnIndex("username"));
                break;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return me;
    }
    public void storeFriendRequest(int senderID,String senderName,String senderNickname, String url,String extras,String time){
        XunChatDatabaseHelper xunChatDatabaseHelper = new XunChatDatabaseHelper(XunApplication.getContext(),"XunChat.db",null);
        SQLiteDatabase database = xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String me="";
        Cursor cursor = database.rawQuery("SELECT username FROM user WHERE isActive=?",new String[]{"1"} );
        if (cursor.moveToFirst()){
            do{
                me  = cursor.getString(cursor.getColumnIndex("username"));
                break;
            }while (cursor.moveToNext());
        }
        cursor.close();
        if (!me.isEmpty()){
            String sender_name = "";
            contentValues.put("sender_id",String.valueOf(senderID));
            contentValues.put("sender",senderName);
            contentValues.put("sender_nickname",senderNickname);
            contentValues.put("extras",extras);
            contentValues.put("isRead","0");
            contentValues.put("isAgreed","0");
            contentValues.put("time",time);
            contentValues.put("url",MainActivity.domain_url+url);
            contentValues.put("username",me);
            Cursor cursorRequest = database.rawQuery("SELECT sender FROM request WHERE username=? AND sender=?",new String[]{me,senderName});
            if (cursorRequest.moveToFirst()){
                do {
                    sender_name = cursorRequest.getString(cursorRequest.getColumnIndex("sender"));
                }while (cursorRequest.moveToNext());

            }
            cursorRequest.close();
            if (sender_name.isEmpty()){
                database.insert("request",null,contentValues);
            }else {
                database.update("request",contentValues,"username=? AND sender=?",new String[]{me,senderName});
            }
        }

    }
}
