package com.example.xunhu.xunchat.Model.AsyTasks;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.example.xunhu.xunchat.R;
import com.squareup.picasso.Picasso;

/**
 * Created by xunhu on 7/8/2017.
 */

public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView imageView){
        if (url!=null && url.length()>0){
            Picasso.with(c).load(url).placeholder(R.drawable.ic_loading).into(imageView);
        }else {
            Picasso.with(c).load(R.drawable.ic_loading).into(imageView);
        }
    }
    public static void loadImageUri(Context c, Uri uri, ImageView imageView){
        if (uri!=null &&uri.toString().length()>0){
            Picasso.with(c).load(uri).placeholder(R.drawable.ic_loading).into(imageView);
        }else {
            Picasso.with(c).load(R.drawable.ic_loading).into(imageView);
        }
    }
}
