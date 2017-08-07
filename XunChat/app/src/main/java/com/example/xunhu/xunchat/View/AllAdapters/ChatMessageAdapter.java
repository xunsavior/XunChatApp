package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.xunhu.xunchat.View.Activities.ProfileThemeActivity_;
import com.example.xunhu.xunchat.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
        final ViewHolder holder;
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
                    holder.llImageRightLayout.setVisibility(View.GONE);
                    if (message.getIsSentSuccess()==0){
                        holder.tvRightMessage.setError("fail to send your message"+
                                "\n caused by network error or the user is not your friend now!");
                    }
                    holder.tvRightMessage.setText(messageContent);
                    break;
                case 1:
                    holder.tvRightMessage.setVisibility(View.GONE);
                    holder.llAudioMessage.setVisibility(View.GONE);
                    holder.llImageRightLayout.setVisibility(View.VISIBLE);
                    try {
                        JSONObject object = new JSONObject(messageContent);
                        String caption = object.getString("image_caption");
                        final String imageUrl = MainActivity.domain_url+object.getString("image_url");
                        if (message.getIsSentSuccess()==0){
                            holder.tvRightCaption.setError("fail to send this message"+
                                    "\n caused by network error or the user is not your friend now!");
                        }
                        holder.tvRightCaption.setText(caption);
                        PicassoClient.downloadImage(context,imageUrl,holder.ivRightPhoto);
                        holder.ivRightPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                enlargePhoto(imageUrl);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    holder.tvRightMessage.setVisibility(View.GONE);
                    holder.llAudioMessage.setVisibility(View.VISIBLE);
                    holder.llImageRightLayout.setVisibility(View.GONE);
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
                    holder.llImageLeftLayout.setVisibility(View.GONE);
                    holder.tvLeftMessage.setText(messageContent);
                    break;
                case 1:
                    holder.tvLeftMessage.setVisibility(View.GONE);
                    holder.LlLeftAudioMessage.setVisibility(View.GONE);
                    holder.llImageLeftLayout.setVisibility(View.VISIBLE);
                    try {
                        JSONObject object = new JSONObject(message.getMessageContent());
                        String caption = object.getString("image_caption");
                        final String imageUrl = MainActivity.domain_url+object.getString("image_url");
                        holder.tvLeftCaption.setText(caption);
                        holder.ivLeftPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                enlargePhoto(imageUrl);
                            }
                        });
                        PicassoClient.downloadImage(context,imageUrl,holder.ivLeftPhoto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    holder.tvLeftMessage.setVisibility(View.GONE);
                    holder.LlLeftAudioMessage.setVisibility(View.VISIBLE);
                    holder.llImageLeftLayout.setVisibility(View.GONE);
                    holder.LlLeftAudioMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
        }
    }
    public void playAudio(String base64Code,String filename){
        File file = new File(filename);
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
        } catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    void enlargePhoto(String url){
        if (!url.isEmpty()){
            Intent intent = new Intent(getContext(), ProfileThemeActivity_.class);
            intent.putExtra("url",url);
            getContext().startActivity(intent);
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
        @BindView(R.id.tv_message_time) TextView tvMessageTime;
        @BindView(R.id.rl_friend_left) RelativeLayout rlFriendLeft;
        @BindView(R.id.iv_message_left_image) CircleImageView ivLeftImage;
        @BindView(R.id.tv_left_message) TextView tvLeftMessage;
        @BindView(R.id.rl_friend_right) RelativeLayout rlFriendRight;
        @BindView(R.id.tv_right_message) TextView tvRightMessage;
        @BindView(R.id.iv_message_right_image) CircleImageView ivRightImage;
        @BindView(R.id.ll_audio_right_layout) LinearLayout llAudioMessage;
        @BindView(R.id.ll_audio_left_layout) LinearLayout LlLeftAudioMessage;
        @BindView(R.id.ll_image_left_layout) LinearLayout llImageLeftLayout;
        @BindView(R.id.ivLeftPhoto) ImageView ivLeftPhoto;
        @BindView(R.id.tvLeftCaption) TextView tvLeftCaption;
        @BindView(R.id.ll_image_right_layout) LinearLayout llImageRightLayout;
        @BindView(R.id.ivRightPhoto) ImageView ivRightPhoto;
        @BindView(R.id.tvRightCaption) TextView tvRightCaption;


        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
