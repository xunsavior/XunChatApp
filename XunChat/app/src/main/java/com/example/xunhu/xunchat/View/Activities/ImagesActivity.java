package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.InfiniteCardViewAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 8/18/2017.
 */
@EActivity(R.layout.post_images_activity_layout)
public class ImagesActivity extends Activity {
    @ViewById
    HorizontalInfiniteCycleViewPager cycledView;
    ArrayList<String> urls = new ArrayList<>();
    InfiniteCardViewAdapter adapter;
    @AfterViews
    void setImagesActivityViews(){
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        urls = (ArrayList<String>) getIntent().getSerializableExtra("urls");
        adapter = new InfiniteCardViewAdapter(urls,getBaseContext());
        cycledView.setAdapter(adapter);
    }
}
