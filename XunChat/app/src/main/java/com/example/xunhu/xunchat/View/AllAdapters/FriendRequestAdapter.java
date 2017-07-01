package com.example.xunhu.xunchat.View.AllAdapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by xunhu on 6/30/2017.
 */

public class FriendRequestAdapter extends ArrayAdapter<Request> {
    Context context;
    int resourceId;
    List<Request> requests;
    float X = 0;
    float Y =0;
    public FriendRequestAdapter(@NonNull Context context, int resource, @NonNull List<Request> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId=resource;
        this.requests=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Request request = getItem(position);
        final ViewHolder holder;
        View view;
        if (convertView!=null){
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if (request.getIsAgreed().equals("false")){
            holder.tvAdded.setVisibility(View.INVISIBLE);
            holder.btnAccept.setVisibility(View.VISIBLE);
        }else {
            holder.tvAdded.setVisibility(View.VISIBLE);
            holder.btnAccept.setVisibility(View.INVISIBLE);
        }
        if (request.getIsRead().equals("true")){
            holder.tvNew.setVisibility(View.INVISIBLE);
        }else {
            holder.tvNew.setVisibility(View.VISIBLE);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requests.remove(request);
                notifyDataSetChanged();
            }
        });
        holder.rlFriendRequest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        X = event.getRawX();
                        Y = event.getRawY();
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (Y-event.getRawY()>150 || Y-event.getRawY()<-150){
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    case MotionEvent.ACTION_UP:
                        if (X-event.getRawX()<-200){
                            holder.tvDelete.setVisibility(View.VISIBLE);
                        }else if (X-event.getRawX()>200){
                            holder.tvDelete.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        holder.ivRequestProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("type","accept");
                        User user = new User(request.getSender(),request.getSenderNickname(),
                        request.getSenderUrl(),request.getSenderGender(),request.getSenderRegion(),
                        request.getSenderWhatsup(),Integer.parseInt(request.getSenderAge()),"");
                        intent.putExtra("user",user);
                        context.getApplicationContext().startActivity(intent);
            }
        });
        holder.ivRequestProfile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        holder.rlFriendRequest.setBackgroundColor(Color.GRAY);
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        holder.rlFriendRequest.setBackgroundColor(Color.WHITE);
                        return false;
                    case MotionEvent.ACTION_UP:
                        holder.rlFriendRequest.setBackgroundColor(Color.WHITE);
                        return false;
                    default:
                        break;
                }
                return false;
            }
        });
        holder.tvDelete.setVisibility(View.GONE);
        holder.tvUsername.setText(request.getSender());
        holder.tvExtras.setText(request.getExtras());
        Picasso.with(context).load(request.getSenderUrl()).into(holder.ivRequestProfile);
        return view;
    }

     class ViewHolder{
        @BindView(R.id.iv_request_profile)
        ImageView ivRequestProfile;
        @BindView(R.id.tv_sender_username)
        TextView tvUsername;
        @BindView(R.id.tv_sender_extras)
        TextView tvExtras;
        @BindView(R.id.btn_accept)
        Button btnAccept;
        @BindView(R.id.tv_added)
        TextView tvAdded;
        @BindView(R.id.tv_latest_request)
        TextView tvNew;
        @BindView(R.id.rl_whole_friend_request)
        RelativeLayout rlFriendRequest;
        @BindView(R.id.tv_request_delete)
        TextView tvDelete;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
