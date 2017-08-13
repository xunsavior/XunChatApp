package com.example.xunhu.xunchat.View.AllAdapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.xunhu.xunchat.Model.Entities.Image;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.PhotoGalleryActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 8/12/2017.
 */

public class GalleryGridViewAdapter extends RecyclerView.Adapter<GalleryGridViewAdapter.ViewHolder> {
    Activity activity;
    GalleryGridViewInterface comm;
    List<Image> images = new ArrayList<>();
    public GalleryGridViewAdapter(Activity activity,GalleryGridViewInterface comm,List<Image> images){
        this.activity=activity;
        this.comm=comm;
        this.images=images;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_image_adapter_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.get(position).getIsChecked()==0){
                    if (PhotoGalleryActivity.selectedPhoto<3){
                        comm.addUri(position);
                        images.get(position).setIsChecked(1);
                        notifyItemChanged(position);
                    }else {
                        Toast.makeText(activity,"You can only selected maximum 3 images",Toast.LENGTH_SHORT).show();
                    }
                }else if (images.get(position).getIsChecked()==1){
                    comm.removeUri(position);
                    images.get(position).setIsChecked(0);
                    notifyItemChanged(position);
                }
            }
        });
        if (images.get(position).getIsChecked()==0){
            holder.ivCheck.setImageResource(R.drawable.unchecked_icon);
        }else {
            holder.ivCheck.setImageResource(R.drawable.checked_icon);
        }
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(activity.getContentResolver(),
                Long.parseLong(images.get(position).getUri().getLastPathSegment()),
                MediaStore.Images.Thumbnails.MINI_KIND,null);
        holder.ivPhoto.setImageBitmap(bitmap);
    }
    @Override
    public int getItemCount() {
        return images.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPhoto,ivCheck;
        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivSingleImage);
            ivCheck = (ImageView) itemView.findViewById(R.id.ivCheck);
        }
    }
    public interface GalleryGridViewInterface{
        void addUri(int position);
        void removeUri(int position);
    }
}
