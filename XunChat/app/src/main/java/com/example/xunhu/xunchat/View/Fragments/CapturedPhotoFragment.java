package com.example.xunhu.xunchat.View.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xunhu on 7/30/2017.
 */
@EFragment(R.layout.sending_photo_fragment_layout)
public class CapturedPhotoFragment extends Fragment {
    @ViewById ImageView ivCapturedPhoto;
    Bitmap bitmap = null;
    @AfterViews void setCapturedPhotoViews(){
       ivCapturedPhoto.setImageBitmap(bitmap);
       PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(ivCapturedPhoto);
       photoViewAttacher.update();
    }
    public void setBackPhoto(byte[] bytes){
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
        bitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
    }
    public void setFrontPhoto(byte[] bytes){
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Matrix matrix = new Matrix();
        matrix.preScale(-1f,1f);
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
    public void setSelectedPhoto(Bitmap bitmap){
        this.bitmap=bitmap;
    }
}
