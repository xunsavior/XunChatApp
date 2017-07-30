package com.example.xunhu.xunchat.View.Fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.CameraViewActivity;
import com.example.xunhu.xunchat.View.AllAdapters.PhotoGalleryAdapter;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;
import java.io.IOException;

/**
 * Created by xunhu on 7/30/2017.
 */
@EFragment(R.layout.photo_capture_fragment)
public class CameraViewFragment extends Fragment implements SurfaceHolder.Callback,
       LoaderManager.LoaderCallbacks<Cursor>,PhotoGalleryAdapter.PhotoSelectListener{
    @ViewById SurfaceView svChatCamera;
    @ViewById RecyclerView rvPhotoGallery;
    private final static int MEDIA_STORE_LOADER_ID = 0;
    SurfaceHolder surfaceHolder;
    CameraSource cameraSource;
    FaceDetector faceDetector;
    PhotoGalleryAdapter adapter;
    @AfterViews void setCameraViewFragment(){
        svChatCamera.setZOrderMediaOverlay(true);
        surfaceHolder = svChatCamera.getHolder();
        surfaceHolder.addCallback(this);
        adapter = new PhotoGalleryAdapter(getActivity(),this);
        faceDetector = new FaceDetector.Builder(getActivity())
                .setProminentFaceOnly(true)
                .build();
        rvPhotoGallery.setHasFixedSize(true);
        rvPhotoGallery.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvPhotoGallery.setAdapter(adapter);
        getActivity().getLoaderManager().initLoader(MEDIA_STORE_LOADER_ID,null,  this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder=holder;
        startCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (cameraSource!=null){
            cameraSource.release();
            cameraSource=null;
        }
        if (faceDetector!=null){
            faceDetector.release();
            faceDetector=null;
        }
    }
    void startCamera(){
        cameraSource = new CameraSource.Builder(getActivity(),faceDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .build();
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                requestPermissions(new String[] {android.Manifest.permission.CAMERA}
                        , CameraViewActivity.ACCESS_CAMERA);
            }
        }else {
            try {
                cameraSource.start(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==CameraViewActivity.ACCESS_CAMERA){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"We must access your camera!",Toast.LENGTH_SHORT).show();
            }else {
                startCamera();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //details of each column we are looking
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
        };
        //specify my selection criteria (SQL where...)
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE+"="
                +MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
        return new android.content.CursorLoader(
                getActivity(),
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    @Override
    public void photoSelected(View view, int position) {

    }
}
