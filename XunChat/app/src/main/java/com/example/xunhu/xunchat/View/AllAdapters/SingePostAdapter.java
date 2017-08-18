package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Post;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ImagesActivity;
import com.example.xunhu.xunchat.View.Activities.ImagesActivity_;
import com.example.xunhu.xunchat.View.MainActivity;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xunhu on 8/18/2017.
 */

public class SingePostAdapter extends RecyclerView.Adapter<SingePostAdapter.ViewHolder> {
    Context context;
    List<Post> posts = new ArrayList<>();
    public SingePostAdapter(Context context,List<Post> posts){
           this.context=context;
           this.posts=posts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.post_unit_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        PicassoClient.downloadImage(context,MainActivity.domain_url+post.getImageURL(),holder.civPostProfileImage);
        holder.tvPostUsername.setText(post.getNickName());
        holder.tvPostTime.setText(MainActivity.timeGap(Long.parseLong(post.getTimestamp())));
        switch (post.getPostType()){
            case 0:
                holder.llImageLayout.setVisibility(View.GONE);
                if (post.getLocation().isEmpty()){
                    holder.tvPostLocation.setText("not public");
                }else {
                    holder.tvPostLocation.setText(post.getLocation());
                }
                holder.tvPostContent.setText(post.getPostContent());
                break;
            case 1:
                final ArrayList<String> urls = new ArrayList<>();
                holder.llImageLayout.setVisibility(View.VISIBLE);
                holder.tvPostContent.setText(post.getCaption());
                holder.llImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagesActivity_.intent(context).extra("urls", urls).start();
                    }
                });
                try {
                    JSONObject object = new JSONObject(post.getPostContent());
                    JSONArray jsonArray = new JSONArray(object.getString("images"));
                    for (int i=0;i<jsonArray.length();i++){
                        urls.add(jsonArray.getString(i));
                        switch (i){
                            case 0:
                                PicassoClient.downloadImage(context,MainActivity.domain_url+urls.get(0),holder.ivPostOne);
                                break;
                            case 1:
                                PicassoClient.downloadImage(context,MainActivity.domain_url+urls.get(1),holder.ivPostTwo);
                                break;
                            default:
                                PicassoClient.downloadImage(context,MainActivity.domain_url+urls.get(2),holder.ivPostThree);
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civPostProfileImage)
        CircleImageView civPostProfileImage;
        @BindView(R.id.llImageLayout)
        LinearLayout llImageLayout;
        @BindView(R.id.tvPostUsername)
        TextView tvPostUsername;
        @BindView(R.id.tvPostTime)
        TextView tvPostTime;
        @BindView(R.id.tvPostLocation)
        TextView tvPostLocation;
        @BindView(R.id.tvPostContent)
        TextView tvPostContent;
        @BindView(R.id.ivThumbsUp)
        ImageView ivThumbsUp;
        @BindView(R.id.tvNumOfLikes)
        TextView tvNumOfLikes;
        @BindView(R.id.ivPostOne)
        ImageView ivPostOne;
        @BindView(R.id.ivPostTwo)
        ImageView ivPostTwo;
        @BindView(R.id.ivPostThree)
        ImageView ivPostThree;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
