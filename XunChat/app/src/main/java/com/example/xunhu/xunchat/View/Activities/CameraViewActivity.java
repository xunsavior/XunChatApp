package com.example.xunhu.xunchat.View.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.example.xunhu.xunchat.View.XunApplication.getContext;

/**
 * Created by xunhu on 7/15/2017.
 */

public class CameraViewActivity extends Activity implements SurfaceHolder.Callback,Detector.Processor {
    SurfaceView cameraPreview;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceHolder surfaceHolder;
    public static final int ACCESS_CAMERA = 200;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity_layout);
        cameraPreview = (SurfaceView) findViewById(R.id.sv_camera);
        cameraPreview.setZOrderMediaOverlay(true);
        cameraPreview.getLayoutParams().width= MainActivity.getScreenWidth();
        cameraPreview.getLayoutParams().height= MainActivity.getScreenWidth();
        surfaceHolder = cameraPreview.getHolder();
        barcodeDetector = new BarcodeDetector.Builder(this).
                setBarcodeFormats(Barcode.QR_CODE).build();
        if (!barcodeDetector.isOperational()){
            Toast.makeText(getApplicationContext(),"Sorry, could not setup the detector",Toast.LENGTH_SHORT).show();
            finish();
        }
        barcodeDetector.setProcessor(this);
        surfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder=holder;
        startCamera();
    }
    public void startCamera(){
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
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
                        , ACCESS_CAMERA);
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (cameraSource!=null){
            cameraSource.release();
            cameraSource=null;
        }
        if (barcodeDetector!=null){
            barcodeDetector.release();
            barcodeDetector=null;
        }
    }
    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        final SparseArray<Barcode> barcodeSparseArray = detections.getDetectedItems();
        if (barcodeSparseArray.size()>0){
            Intent intent = new Intent();
            intent.putExtra("qr_code",barcodeSparseArray.valueAt(0).rawValue);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ACCESS_CAMERA){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"We must access your camera!",Toast.LENGTH_SHORT).show();
            }else {
                startCamera();
            }
        }
    }
}
