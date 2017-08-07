package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Window;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment;
import com.example.xunhu.xunchat.View.Fragments.CameraViewFragment_;
import com.example.xunhu.xunchat.View.Fragments.CapturedPhotoFragment;
import com.example.xunhu.xunchat.View.Fragments.CapturedPhotoFragment_;
import com.example.xunhu.xunchat.View.MainActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


/**
 * Created by xunhu on 7/30/2017.
 */
@Fullscreen
@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.photo_taken_activity)
public class PhotoTakenActivity extends AppCompatActivity implements
        CameraViewFragment.CameraViewFragmentInterface, CapturedPhotoFragment.CapturedPhotoFragmentInterface {
    CameraViewFragment cameraViewFragment;
    CapturedPhotoFragment capturedPhotoFragment;
    boolean isBackShowing = false;
    @AfterViews void setPhotoTakenActivity(){
        cameraViewFragment = new CameraViewFragment_();
        capturedPhotoFragment = new CapturedPhotoFragment_();
        getFragmentManager().beginTransaction().replace(R.id.photo_container,cameraViewFragment).commit();
    }
    @Override
    public void convertPhoto(byte[] bytes,int cameraFace) {
        if (cameraFace== CameraSource.CAMERA_FACING_BACK){
            capturedPhotoFragment.setBackPhoto(bytes);
        }else {
            capturedPhotoFragment.setFrontPhoto(bytes);
        }
        flipFragment();
    }

    @Override
    public void convertSelectedPhoto(Bitmap bitmap) {
        capturedPhotoFragment.setSelectedPhoto(bitmap);
        flipFragment();
    }
    private void flipFragment(){
        if (isBackShowing){
            getFragmentManager().popBackStack();
            isBackShowing=false;
        }else {
            isBackShowing=true;
            getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out,R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out).replace(R.id.photo_container,capturedPhotoFragment).
                    addToBackStack(null).commit();
        }
    }
    @Override
    public void onBackPressed() {
        isBackShowing=false;
        super.onBackPressed();
    }
    @Override
    public void sendPhoto(Bitmap bitmap,String caption,long timestamp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String imageCode = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        JSONObject object = new JSONObject();
        try {
            object.put("image_caption",caption);
            object.put("image_url","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("message_type",1);
        contentValues.put("me_or_friend",0);
        contentValues.put("message_content",object.toString());
        contentValues.put("time",String.valueOf(timestamp));
        contentValues.put("username",MainActivity.me.getUsername());
        contentValues.put("is_sent",1);
        contentValues.put("friend_username",ChatBoardActivity.user.getUsername());
        database.insert("message",null,contentValues);
        contentValues.clear();
        updateLatestMessage(String.valueOf(timestamp));
        Intent intent = new Intent();
        intent.putExtra("bitmap",imageCode);
        intent.putExtra("caption",caption);
        intent.putExtra("timestamp",timestamp);
        setResult(RESULT_OK,intent);
        finish();
    }
    void updateLatestMessage(String timestamp){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select friend_username,username from latest_message where friend_username=? and username=?",
                new String[]{ChatBoardActivity.user.getUsername(),MainActivity.me.getUsername()});
        String friendUsername = "";
        String myUsername = "";
        if (cursor.moveToFirst()){
            do {
                friendUsername = cursor.getString(cursor.getColumnIndex("friend_username"));
                myUsername = cursor.getString(cursor.getColumnIndex("username"));
            }while (cursor.moveToNext());
        }
        cursor.close();
        if (friendUsername.isEmpty()==true && myUsername.isEmpty()==true){
            ContentValues contentValues = new ContentValues();
            contentValues.put("friend_id",ChatBoardActivity.user.getUserID());
            contentValues.put("friend_username",ChatBoardActivity.user.getUsername());
            contentValues.put("friend_nickname",ChatBoardActivity.user.getRemark());
            contentValues.put("friend_url",ChatBoardActivity.user.getUrl());
            contentValues.put("friend_latest_message","[photo]");
            contentValues.put("friend_time",timestamp);
            contentValues.put("type",1);
            contentValues.put("unread",0);
            contentValues.put("username",MainActivity.me.getUsername());
            database.insert("latest_message",null,contentValues);
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("friend_latest_message","[photo]");
            contentValues.put("friend_time",timestamp);
            database.update("latest_message",contentValues,
                    "username=? and friend_username=?",
                    new String[]{MainActivity.me.getUsername(),ChatBoardActivity.user.getUsername()});
        }
    }
}
