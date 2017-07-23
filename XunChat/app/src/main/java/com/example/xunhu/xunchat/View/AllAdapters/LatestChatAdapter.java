package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.LatestMessage;
import com.example.xunhu.xunchat.Model.Entities.Message;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Model.Services.XunChatReceiveMessageService;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ChatBoardActivity;
import com.example.xunhu.xunchat.View.MainActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

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
        final LatestMessage latestMessage = getItem(position);
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
        String message = latestMessage.getLatestMessage();
        if (latestMessage.getMessageType()==0){
           if (message.length()>20){
               holder.tvMessage.setText(message.substring(0,21)+"...");
           }else {
               holder.tvMessage.setText(message);
           }
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
        holder.llLatestChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatBoardActivity.class);
                User user = new User(latestMessage.getFriendID(),latestMessage.getFriendUsername(),
                        latestMessage.getFriendNickname(),latestMessage.getFriendURL(),
                        "","","",-1,1);
                user.setRemark(latestMessage.getFriendNickname());
                intent.putExtra("user",user);
                getContext().startActivity(intent);
            }
        });
        holder.llLatestChat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_latest_chat:
                                deleteLatestChat(latestMessage);
                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.delete_latest_chat);
                popup.show();
                return false;
            }
        });
        return view;
    }
    public void deleteLatestChat(LatestMessage latestMessage){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        database.delete("latest_message","username=? and friend_username=?",
                new String[]{MainActivity.me.getUsername(),latestMessage.getFriendUsername()});
        database.delete("message","username=? and friend_username=?",
                new String[]{MainActivity.me.getUsername(),latestMessage.getFriendUsername()});
       String dir = Environment.getExternalStorageDirectory()+
                File.separator+MainActivity.me.getUsername()+
                File.separator+latestMessage.getFriendUsername();
       File files = new File(dir);
       if (files.listFiles().length>0){
            for (File file: files.listFiles()){
                System.out.println("@ delete "+file.getName()+" space "+file.length()/1024+" KB");
                    file.delete();
            }
       }
        Intent intent = new Intent(XunChatReceiveMessageService.REFRESH_CHAT_FRAGMENT);
        getContext().sendBroadcast(intent);
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
        @BindView(R.id.ll_latest_chat)
        LinearLayout llLatestChat;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @OnTouch({R.id.ll_latest_chat})
        public boolean onTouchRespond(MotionEvent motionEvent){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    llLatestChat.setBackgroundColor(Color.parseColor("#00BFFF"));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    llLatestChat.setBackgroundColor(Color.WHITE);
                    break;
                case MotionEvent.ACTION_UP:
                    llLatestChat.setBackgroundColor(Color.WHITE);
                    break;
                default:
                    break;
            }
            return false;
        }

    }
}
