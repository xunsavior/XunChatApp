package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Message;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xunhu on 7/18/2017.
 */

public class ChatMessageAdapter extends ArrayAdapter<Message> {
    int resourceId;
    Context context;
    User user;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public ChatMessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects, User user) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.context=context;
        this.user=user;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder holder;
        final Message message = getItem(position);
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
                    holder.tvRightMessage.setVisibility(View.VISIBLE);
                    holder.llAudioMessage.setVisibility(View.GONE);
                    if (message.getIsSentSuccess()==0){
                        holder.tvRightMessage.setText(messageContent);
                        holder.tvRightMessage.setError("fail to send your message"+
                                "\n caused by network error or the user is not your friend now!");
                    }else {
                        holder.tvRightMessage.setText(messageContent);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    holder.tvRightMessage.setVisibility(View.GONE);
                    holder.llAudioMessage.setVisibility(View.VISIBLE);
                    holder.llAudioMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String filename = message.getTime()+".3gp";
                            String audioOutput = Environment.getExternalStorageDirectory()+
                                    File.separator+ MainActivity.me.getUsername()+
                                    File.separator+user.getUsername()+
                                    File.separator+filename;
                            playAudio(message.getMessageContent(),audioOutput);
                        }
                    });
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
                    holder.tvLeftMessage.setVisibility(View.VISIBLE);
                    holder.LlLeftAudioMessage.setVisibility(View.GONE);

                    holder.tvLeftMessage.setText(messageContent);
                    break;
                case 1:
                    break;
                case 2:
                    holder.tvLeftMessage.setVisibility(View.GONE);
                    holder.LlLeftAudioMessage.setVisibility(View.VISIBLE);

                    holder.LlLeftAudioMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            String filename = message.getTime()+".3gp";
//                            String audioOutput = Environment.getExternalStorageDirectory()+
//                                    File.separator+ MainActivity.me.getUsername()+
//                                    File.separator+user.getUsername()+
//                                    File.separator+filename;
//                            playAudio(message.getMessageContent(),audioOutput);
                            playAudioFromURL(MainActivity.domain_url+message.getMessageContent());
                        }
                    });
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
    public void playAudioFromURL(String url){
        try {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
            System.out.println("@ runtime "+e.getMessage());
        }
    }
    public void playAudio(String base64Code,String filename){
        File file = new File(filename);
        System.out.print("@ code "+base64Code);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            byte[] bytes = Base64.decode(base64Code,0);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filename);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("@ io "+e.getMessage());
        } catch (RuntimeException e){
            e.printStackTrace();
            System.out.println("@ runtime "+e.getMessage());
        }
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
        CircleImageView ivLeftImage;
        @BindView(R.id.tv_left_message)
        TextView tvLeftMessage;
        @BindView(R.id.rl_friend_right)
        RelativeLayout rlFriendRight;
        @BindView(R.id.tv_right_message)
        TextView tvRightMessage;
        @BindView(R.id.iv_message_right_image)
        CircleImageView ivRightImage;
        @BindView(R.id.ll_audio_right_layout)
        LinearLayout llAudioMessage;
        @BindView(R.id.ll_audio_left_layout)
        LinearLayout LlLeftAudioMessage;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
