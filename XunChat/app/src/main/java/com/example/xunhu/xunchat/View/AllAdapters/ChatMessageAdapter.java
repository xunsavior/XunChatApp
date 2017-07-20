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
import com.example.xunhu.xunchat.Model.Entities.Message;
import com.example.xunhu.xunchat.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunhu on 7/18/2017.
 */

public class ChatMessageAdapter extends ArrayAdapter<Message> {
    int resourceId;
    Context context;
    public ChatMessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder holder;
        Message message = getItem(position);
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String senderURL = message.getSenderURL();
        int messageType = message.getMessageType();
        int from = message.getFrom();
        String messageContent = message.getMessageContent();
        String time = message.getTime();
        if (from==0){
            holder.rlFriendLeft.setVisibility(View.GONE);
            holder.rlFriendRight.setVisibility(View.VISIBLE);
            PicassoClient.downloadImage(context,senderURL,holder.ivRightImage);
            switch (messageType){
                case 0:
                    holder.tvRightMessage.setText(messageContent);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }else if (from==1){
            holder.rlFriendLeft.setVisibility(View.VISIBLE);
            holder.rlFriendRight.setVisibility(View.GONE);
            PicassoClient.downloadImage(context,senderURL,holder.ivLeftImage);
            switch (messageType){
                case 0:
                    holder.tvLeftMessage.setText(messageContent);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
        Long timestamp = Long.parseLong(time);
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = sdf.format(date);
        holder.tvMessageTime.setText(formattedDate);
        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    class ViewHolder{
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;
        @BindView(R.id.rl_friend_left)
        RelativeLayout rlFriendLeft;
        @BindView(R.id.iv_message_left_image)
        ImageView ivLeftImage;
        @BindView(R.id.tv_left_message)
        TextView tvLeftMessage;
        @BindView(R.id.rl_friend_right)
        RelativeLayout rlFriendRight;
        @BindView(R.id.tv_right_message)
        TextView tvRightMessage;
        @BindView(R.id.iv_message_right_image)
        ImageView ivRightImage;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
