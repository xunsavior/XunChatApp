package com.example.xunhu.xunchat.View.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xunhu on 7/30/2017.
 */
@EFragment(R.layout.sending_photo_fragment_layout)
public class CapturedPhotoFragment extends Fragment {
    @ViewById ImageView ivCapturedPhoto;
    @ViewById ImageButton ibSendImage;
    @ViewById EditText etCaption;
    Bitmap bitmap = null;
    byte[] bytes =null;
    CapturedPhotoFragmentInterface comm;
    @AfterViews void setCapturedPhotoViews(){
       ivCapturedPhoto.setImageBitmap(bitmap);
       PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(ivCapturedPhoto);
       photoViewAttacher.update();
    }
    public void setBackPhoto(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
        bitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        this.bitmap = bitmap;
    }
    public void setFrontPhoto(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Matrix matrix = new Matrix();
        matrix.preScale(-1f,1f);
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        this.bitmap =  bitmap;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (CapturedPhotoFragmentInterface) activity;
    }
    public void setSelectedPhoto(Bitmap bitmap){
        this.bitmap=bitmap;
    }
    Bitmap resizeBitmap(Bitmap bitmap){
        float ratio = (float) bitmap.getByteCount()/(float) 1024/(float)1024;
        if (ratio>=1){
            float originalWidth = bitmap.getWidth();
            float originalHeight = bitmap.getHeight();
            float resizeWidth = (float) (originalWidth/Math.sqrt(ratio));
            float resizeHeight = (float) (originalHeight/Math.sqrt(ratio));
            bitmap=Bitmap.createScaledBitmap(bitmap, (int) resizeWidth, (int) resizeHeight, true);
        }
        return bitmap;
    }
    @Click({R.id.ibSendImage})
    void sendingImage(View view){
        switch (view.getId()){
            case R.id.ibSendImage:
                bitmap = resizeBitmap(bitmap);
                long timestamp = System.currentTimeMillis();
                comm.sendPhoto(bitmap,etCaption.getText().toString(),timestamp);
                break;
            default:
                break;
        }
    }
    public interface CapturedPhotoFragmentInterface{
        void sendPhoto(Bitmap bitmap,String caption,long timestamp);
    }
}
