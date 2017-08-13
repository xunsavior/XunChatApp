package com.example.xunhu.xunchat.Model.Entities;

import android.net.Uri;

/**
 * Created by xunhu on 8/13/2017.
 */

public class Image {
    Uri uri;
    int isChecked;
    public Image(Uri uri,int isChecked){
        this.uri=uri;
        this.isChecked=isChecked;
    }
    public Uri getUri() {
        return uri;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
}
