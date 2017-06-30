package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.xunhu.xunchat.Model.AsyTasks.MySingleton;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by xunhu on 6/21/2017.
 */

public class SearchFriendsAdapter extends ArrayAdapter<User> {
    int resource;
    Context context;

    public SearchFriendsAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        final ViewHolder holder;
        View view;
        if (convertView!=null){
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(getContext()).inflate(resource,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvNickName.setText(user.getNickname());
        holder.tvUsername.setText(user.getUsername());
        ImageRequest imageRequest = new ImageRequest(MainActivity.domain_url + user.getUrl(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.ivProfile.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Loading Image Error: "+error,Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(getContext()).addImageRequestToRequestQueue(imageRequest);
        return view;
    }

    static class ViewHolder{
        @BindView(R.id.rl_search_result)
        RelativeLayout llSearchResult;
        @BindView(R.id.iv_search_profile_image)
        ImageView ivProfile;
        @BindView(R.id.tv_friend_nickname)
        TextView tvNickName;
        @BindView(R.id.tv_friend_username)
        TextView tvUsername;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @OnTouch({R.id.rl_search_result})
        public boolean onTouch(MotionEvent event){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    llSearchResult.setBackgroundColor(Color.parseColor("#E1E0DE"));
                    return false;
                default:
                    return false;
            }
        }
    }
}
