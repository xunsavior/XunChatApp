package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by xunhu on 7/15/2017.
 */

public class QRCodeActivity extends Activity {
    GifImageView gifQRCode;
    int height = MainActivity.getScreenWidth();
    int width = MainActivity.getScreenWidth();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_dialog);
        gifQRCode = (GifImageView) findViewById(R.id.iv_gif);
        gifQRCode.getLayoutParams().width= width;
        gifQRCode.getLayoutParams().height=height;
        String username = getIntent().getStringExtra("username");
        createQRCode(username);
    }
    public void createQRCode(String string){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE,width,height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            gifQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
