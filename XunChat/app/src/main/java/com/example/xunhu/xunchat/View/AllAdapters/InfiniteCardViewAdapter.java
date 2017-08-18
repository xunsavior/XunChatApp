package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xunhu on 8/18/2017.
 */

public class InfiniteCardViewAdapter extends PagerAdapter {
    ArrayList<String> images = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    public InfiniteCardViewAdapter(ArrayList<String> images,Context context){
        this.context=context;
        this.images=images;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.imageview_card_layout,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivCardImage);
//        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
//        photoViewAttacher.update();
        PicassoClient.downloadImage(context, MainActivity.domain_url+images.get(position),imageView);
        container.addView(view);
        return view;
    }
}
