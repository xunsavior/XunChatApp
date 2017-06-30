package com.example.xunhu.xunchat.View.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import static java.security.AccessController.getContext;

/**
 * Created by xunhu on 6/14/2017.
 */

public class ProfileThemeActivity extends Activity {
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_image_theme_layout);
        imageView= (ImageView) findViewById(R.id.iv_enlargedProfileImage);
        imageView.getLayoutParams().width=MainActivity.getScreenWidth();
        imageView.getLayoutParams().height=MainActivity.getScreenWidth();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = getIntent().getStringExtra("url");
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error...",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(getApplicationContext()).addImageRequestToRequestQueue(request);
    }
}
