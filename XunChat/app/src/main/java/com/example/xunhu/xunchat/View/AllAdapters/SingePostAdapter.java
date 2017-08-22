package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Post;
import com.example.xunhu.xunchat.Presenter.DeletePostPresenter;
import com.example.xunhu.xunchat.Presenter.LikePostPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ImagesActivity;
import com.example.xunhu.xunchat.View.Activities.ImagesActivity_;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.Activities.WhoLikePostActivity_;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.DeletePostView;
import com.example.xunhu.xunchat.View.Interfaces.LikePostView;
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
public class SingePostAdapter extends RecyclerView.Adapter<SingePostAdapter.ViewHolder>
        implements LikePostView,DeletePostView{
    Context context;
    List<Post> posts = new ArrayList<>();
    LikePostPresenter likePostPresenter;
    DeletePostPresenter deletePostPresenter;
    LikePostView likePostView = this;
    DeletePostView deletePostView = this;
    SinglePostAdapterInterface comm;
    MyDialog myDialog;
    int deletePosition= -1;
    public SingePostAdapter(Context context,List<Post> posts,SinglePostAdapterInterface singlePostAdapterInterface){
           this.context=context;
           this.posts=posts;
           this.comm=singlePostAdapterInterface;
           myDialog = new MyDialog(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.post_unit_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Post post = posts.get(position);
        holder.tvNumOfLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.getNumberLikes()>0){
                    SubActivity.isRefreshNeeded=false;
                    WhoLikePostActivity_.intent(context).extra("post_id",post.getPostID()).start();
                }
            }
        });
        if (post.getIsLiked()==0){
            holder.ivLike.setImageResource(R.drawable.like_icon);
        }else {
            holder.ivLike.setImageResource(R.drawable.liked_icon);
        }
        if (post.getPosterID()==MainActivity.me.getId()){
            holder.ivDeletePost.setVisibility(View.VISIBLE);
            holder.ivDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePosition = position;
                    deletePostPresenter = new DeletePostPresenter(deletePostView);
                    deletePostPresenter.deletePostAction(post);
                    myDialog.createDeletePostDialog();
                }
            });
        }else {
            holder.ivDeletePost.setVisibility(View.GONE);
        }
        PicassoClient.downloadImage(context,MainActivity.domain_url+post.getImageURL(),holder.civPostProfileImage);
        holder.tvPostUsername.setText(post.getNickName());
        holder.tvNumOfLikes.setText(post.getNumberLikes()+" likes");
        holder.tvPostTime.setText(MainActivity.timeGap(Long.parseLong(post.getTimestamp())));
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePostPresenter = new LikePostPresenter(likePostView);
                if (post.getIsLiked()==0){
                    likePostPresenter.likePost(post.getPostID(),MainActivity.me.getId());
                }else {
                    likePostPresenter.cancelLikedPost(post.getPostID(),MainActivity.me.getId());
                }
            }
        });

        if (post.getLocation().isEmpty()){
            holder.llLocationLayout.setVisibility(View.GONE);
        }else {
            holder.llLocationLayout.setVisibility(View.VISIBLE);
            holder.tvPostLocation.setText(post.getLocation());
        }
        switch (post.getPostType()){
            case 0:
                holder.llImageLayout.setVisibility(View.GONE);
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
                                holder.ivPostTwo.setVisibility(View.INVISIBLE);
                                holder.ivPostThree.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                holder.ivPostTwo.setVisibility(View.VISIBLE);
                                PicassoClient.downloadImage(context,MainActivity.domain_url+urls.get(1),holder.ivPostTwo);
                                break;
                            default:
                                holder.ivPostThree.setVisibility(View.VISIBLE);
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

    @Override
    public void likeSuccess(String msg, int postID,int type) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        if (type==0){
            comm.addLike(postID);
        }else {
            comm.cancelLike(postID);
        }
    }
    @Override
    public void likedFail(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess(Post post) {
        myDialog.cancelDeletePostDialog();
        Toast.makeText(context,"The post has been deleted successfully!",Toast.LENGTH_SHORT).show();
        comm.deletePost(post);
    }

    @Override
    public void deleteFail(String msg) {
        myDialog.cancelDeletePostDialog();
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public interface SinglePostAdapterInterface{
        void addLike(int postID);
        void cancelLike(int postID);
        void deletePost(Post post);
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
        @BindView(R.id.tvNumOfLikes)
        TextView tvNumOfLikes;
        @BindView(R.id.ivPostOne)
        ImageView ivPostOne;
        @BindView(R.id.ivPostTwo)
        ImageView ivPostTwo;
        @BindView(R.id.ivPostThree)
        ImageView ivPostThree;
        @BindView(R.id.llLocationLayout)
        LinearLayout llLocationLayout;
        @BindView(R.id.ivLike)
        ImageView ivLike;
        @BindView(R.id.ivDeletePost)
        ImageView ivDeletePost;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
