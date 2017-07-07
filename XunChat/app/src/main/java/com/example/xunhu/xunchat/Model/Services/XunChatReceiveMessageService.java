package com.example.xunhu.xunchat.Model.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.SQLite.XunChatDatabaseHelper;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;
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
    private static final String FRIEND_REQUEST = "friend_request";

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
                String senderURL = object.getString("sender_url");
                String senderExtras = object.getString("sender_extras");
                String time = String.valueOf(System.currentTimeMillis());
                storeFriendRequest(senderID,senderName,senderURL,senderExtras,time);
                sendFriendRequestBroadcast(senderName);
                createFriendRequestNotification(senderName,senderURL);
                break;
            default:
                break;
        }

    }
    public void sendFriendRequestBroadcast(String username){
        Intent intent = new Intent(FRIEND_REQUEST);
        intent.putExtra("username",username);
        sendBroadcast(intent);
    }
    public void createFriendRequestNotification(final String username, String url){
        final android.support.v7.app.NotificationCompat.Builder notification =
                new android.support.v7.app.NotificationCompat.Builder(XunApplication.getContext());
        notification.setAutoCancel(true);
        Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_NOTIFICATION);
        notification.setSmallIcon(R.mipmap.ic_small_logo);
        notification.setTicker(username+" has sent you a friend request");
        notification.setSound(alarmSound);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(username);
        notification.setContentText(" has sent you a friend request.");
        ImageRequest imageRequest = new ImageRequest(MainActivity.domain_url + url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                notification.setLargeIcon(response);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(username,FRIEND_REQUST_NOTIFICATION_ID,notification.build());
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(XunApplication.getContext()).addImageRequestToRequestQueue(imageRequest);
    }

    public void storeFriendRequest(int senderID,String senderName,String url,String extras,String time){
        XunChatDatabaseHelper xunChatDatabaseHelper = new XunChatDatabaseHelper(XunApplication.getContext(),"XunChat.db",null);
        SQLiteDatabase database = xunChatDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String me="";
        Cursor cursor = database.rawQuery("SELECT username FROM user WHERE isActive=?",new String[]{"1"} );
        if (cursor.moveToFirst()){
            do{
                me  = cursor.getString(cursor.getColumnIndex("username"));
            }while (cursor.moveToNext());
        }
        cursor.close();
        if (!me.isEmpty()){
            String sender_name = "";
            contentValues.put("sender_id",String.valueOf(senderID));
            contentValues.put("sender",senderName);
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
