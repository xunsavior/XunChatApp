package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Model.Entities.WhoLikePost;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xunhu on 8/21/2017.
 */

public class WhoLikePostAdapter extends ArrayAdapter<WhoLikePost> {
    int resource;
    Context context;
    public WhoLikePostAdapter(@NonNull Context context, int resource, @NonNull List<WhoLikePost> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WhoLikePost whoLikePost=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.single_who_like_adapter,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        PicassoClient.downloadImage(context, MainActivity.domain_url+whoLikePost.getUrl(),viewHolder.civWhoLikeProfile);
        viewHolder.tvWhoUsername.setText(whoLikePost.getUsername());
        viewHolder.tvWhoNickname.setText(whoLikePost.getNickname());
        viewHolder.tvLikeTimestamp.setText(MainActivity.timeGap(Long.parseLong(whoLikePost.getTimestamp())));
        return view;
    }
    class ViewHolder{
        @BindView(R.id.civWhoLikeProfile)
        CircleImageView civWhoLikeProfile;
        @BindView(R.id.tvWhoNickname)
        TextView tvWhoNickname;
        @BindView(R.id.tvWhoUsername)
        TextView tvWhoUsername;
        @BindView(R.id.tvLikeTimestamp)
        TextView tvLikeTimestamp;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
