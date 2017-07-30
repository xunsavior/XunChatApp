package com.example.xunhu.xunchat.View.Fragments;

import android.app.Fragment;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by xunhu on 7/30/2017.
 */
@EFragment(R.layout.sending_photo_fragment_layout)
public class CapturedPhotoFragment extends Fragment {
    @ViewById ImageView ivCapturedPhoto;
    @AfterViews void setCapturedPhotoViews(){

    }
}
