package com.example.xunhu.xunchat.View.AllAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Friend;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity_;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by xunhu on 7/9/2017.
 */

public class SingleContactAdapter extends ArrayAdapter<Friend> implements SearchFriendInterface{
    Context context;
    int resource;
    MyDialog myDialog;
    MySearchFriendPresenter searchFriendPresenter;
    SearchFriendInterface searchFriendInterface=this;
    Friend globalFriend;
    public SingleContactAdapter(@NonNull Context context, int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        myDialog = new MyDialog((Activity) getContext());
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Friend friend = getItem(position);
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
        viewHolder.llContactUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalFriend = friend;
                searchFriendPresenter = new MySearchFriendPresenter(searchFriendInterface);
                searchFriendPresenter.attemptSearchFriends(friend.getUsername());
                myDialog.createLoadingGifDialog();
            }
        });
        PicassoClient.downloadImage(context, friend.getUrl(),viewHolder.ivProfileImage);
        return view;
    }

    @Override
    public void searchFriendFail(String msg) {
        myDialog.cancelLoadingGifDialog();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadResults(String msg) {
        myDialog.cancelLoadingGifDialog();
        try {
            JSONObject object = new JSONObject(msg);
            int user_id = object.getInt("user_id");
            String username = object.getString("username");
            String nickname =  object.getString("nickname");
            String whatsup = object.getString("what_is_up");
            String url = object.getString("url");
            int age = object.getInt("age");
            String gender = object.getString("gender");
            String region = object.getString("region");
            int relationshipType = object.getInt("relationship_type");
            User user = new User(user_id,username,nickname,url,gender,region,whatsup,age,relationshipType);
            user.setRemark(globalFriend.getNickname());
            ProfileActivity_.intent(getContext()).extra("user",user).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        @BindView(R.id.ll_contact_unit)
        LinearLayout llContactUnit;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @SuppressLint("ResourceType")
        @OnTouch({R.id.ll_contact_unit})
        public boolean onTouchRespond(View view, MotionEvent motionEvent){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    llContactUnit.setBackgroundColor(Color.parseColor("#00BFFF"));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    llContactUnit.setBackgroundColor(Color.WHITE);
                    break;
                case MotionEvent.ACTION_UP:
                    llContactUnit.setBackgroundColor(Color.WHITE);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
