package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Friend;
import com.example.xunhu.xunchat.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunhu on 7/9/2017.
 */

public class SingleContactAdapter extends ArrayAdapter<Friend>{
    Context context;
    int resource;
    public SingleContactAdapter(@NonNull Context context, int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Friend friend = getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView!=null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(getContext()).inflate(resource,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        int visibility = (friend.getFirstOrNot()==true)? View.VISIBLE : View.GONE;
        viewHolder.rlIniLayout.setVisibility(visibility);
        viewHolder.tvIniLetter.setText(friend.getNickname().substring(0,1).toUpperCase());
        viewHolder.tvFriendNickname.setText(friend.getNickname());
        viewHolder.tvFriendUsername.setText(friend.getUsername());
        PicassoClient.downloadImage(context, friend.getUrl(),viewHolder.ivProfileImage);
        return view;
    }
    class ViewHolder{
        @BindView(R.id.rl_ini_letter)
        RelativeLayout rlIniLayout;
        @BindView(R.id.tv_ini_letter)
        TextView tvIniLetter;
        @BindView(R.id.iv_search_profile_image)
        ImageView ivProfileImage;
        @BindView(R.id.tv_friend_nickname)
        TextView tvFriendNickname;
        @BindView(R.id.tv_friend_username)
        TextView tvFriendUsername;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
