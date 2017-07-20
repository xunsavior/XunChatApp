package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.LatestMessage;
import com.example.xunhu.xunchat.Model.Entities.Message;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunhu on 7/20/2017.
 */

public class LatestChatAdapter extends ArrayAdapter<LatestMessage> {
    int resourceID;
    Context context;

    public LatestChatAdapter(@NonNull Context context, int resource, @NonNull List<LatestMessage> objects) {
        super(context, resource, objects);
        this.resourceID=resource;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LatestMessage latestMessage = getItem(position);
        ViewHolder holder;
        View view;
        if (convertView!=null){
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.latest_chat_unit_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        PicassoClient.downloadImage(context,MainActivity.domain_url+latestMessage.getFriendURL(),holder.ivProfileImage);
        if (latestMessage.getMessageType()==0){
            holder.tvMessage.setText(latestMessage.getLatestMessage());
        }else if (latestMessage.getMessageType()==1){
            holder.tvMessage.setText("[photo]");
        }else if (latestMessage.getMessageType()==2){
            holder.tvMessage.setText("[audio]");
        }
        if (latestMessage.getUnread()==0){
            holder.tvUnreadMessage.setVisibility(View.INVISIBLE);
        }else {
            holder.tvUnreadMessage.setVisibility(View.VISIBLE);
            holder.tvUnreadMessage.setText(String.valueOf(latestMessage.getUnread()));
        }

        holder.tvNickname.setText(latestMessage.getFriendNickname());
        Long timestamp = Long.parseLong(latestMessage.getTimestamp());
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = sdf.format(date);
        holder.tvTimestamp.setText(formattedDate);
        return view;
    }

    class ViewHolder{
        @BindView(R.id.iv_chat_profile_image)
        ImageView ivProfileImage;
        @BindView(R.id.tv_latest_chat_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_latest_message)
        TextView tvMessage;
        @BindView(R.id.tv_latest_time)
        TextView tvTimestamp;
        @BindView(R.id.tv_unread_messages)
        TextView tvUnreadMessage;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
