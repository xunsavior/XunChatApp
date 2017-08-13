package com.example.xunhu.xunchat.View.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;
import com.example.xunhu.xunchat.View.XunApplication;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by xunhu on 7/8/2017.
 */
public class MyNotification {
    int notificationID;
    Context context = XunApplication.getContext();
    final android.support.v7.app.NotificationCompat.Builder notification =
            new android.support.v7.app.NotificationCompat.Builder(context);
    Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);
    public MyNotification(int notificationID){
        this.notificationID=notificationID;
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_small_logo);
        notification.setSound(alarmSound);
        notification.setWhen(System.currentTimeMillis());
    }
    public void createRequestRespondNotification(final String username, String url,String ticker,String contentText){
        notification.setTicker(ticker);
        notification.setContentTitle(username);
        notification.setContentText(contentText);
        ImageRequest imageRequest = new ImageRequest(MainActivity.domain_url + url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                notification.setLargeIcon(response);
                Intent intent = new Intent(context,MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(username,notificationID,notification.build());
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(XunApplication.getContext()).addImageRequestToRequestQueue(imageRequest);
    }
}
