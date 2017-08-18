package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 8/18/2017.
 */

public class InfiniteCardViewAdapter extends PagerAdapter {
    List<String> images = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    public InfiniteCardViewAdapter(Context context,List<String> images){
        this.context=context;
        this.images=images;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.imageview_card_layout,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivCardImage);
        PicassoClient.downloadImage(context,images.get(position),imageView);
        container.addView(view);
        return view;
    }
}
