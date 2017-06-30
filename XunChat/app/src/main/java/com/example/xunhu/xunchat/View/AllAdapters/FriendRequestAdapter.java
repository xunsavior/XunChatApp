package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunhu on 6/30/2017.
 */

public class FriendRequestAdapter extends ArrayAdapter<Request> {
    Context context;
    int resourceId;

    public FriendRequestAdapter(@NonNull Context context, int resource, @NonNull List<Request> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Request request = getItem(position);
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
        holder.tvUsername.setText(request.getSender());
        holder.tvExtras.setText(request.getExtras());
        Picasso.with(context).load(request.getSenderUrl()).into(holder.ivRequestProfile);
        return view;
    }

    static class ViewHolder{
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
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
