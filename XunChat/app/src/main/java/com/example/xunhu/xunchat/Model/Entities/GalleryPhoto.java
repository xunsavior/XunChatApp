package com.example.xunhu.xunchat.Model.Entities;

import android.net.Uri;

/**
 * Created by xunhu on 7/30/2017.
 */

public class GalleryPhoto {
    Uri uri;
    public GalleryPhoto(Uri uri){
        this.uri=uri;
    }
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
