package com.example.xunhu.xunchat.View.AllAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.xunhu.xunchat.Model.AsyTasks.PicassoClient;
import com.example.xunhu.xunchat.Model.Entities.Request;
import com.example.xunhu.xunchat.Model.Entities.User;
import com.example.xunhu.xunchat.Presenter.MyDeclineRequestPresenter;
import com.example.xunhu.xunchat.Presenter.MySearchFriendPresenter;
import com.example.xunhu.xunchat.Presenter.RequestRespondPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity;
import com.example.xunhu.xunchat.View.Activities.ProfileActivity_;
import com.example.xunhu.xunchat.View.Activities.SubActivity;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Fragments.ContactsFragment;
import com.example.xunhu.xunchat.View.Fragments.RemarkDialogFragment;
import com.example.xunhu.xunchat.View.Interfaces.DeclineRequestView;
import com.example.xunhu.xunchat.View.Interfaces.RequestRespondView;
import com.example.xunhu.xunchat.View.Interfaces.SearchFriendInterface;
import com.example.xunhu.xunchat.View.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.example.xunhu.xunchat.View.MainActivity.xunChatDatabaseHelper;

/**
 * Created by xunhu on 6/30/2017.
 */

public class FriendRequestAdapter extends ArrayAdapter<Request> implements RequestRespondView,
        SearchFriendInterface,DeclineRequestView{
    Context context;
    int resourceId;
    List<Request> requests;
    float X = 0;
    float Y =0;
    int index;
    Request deleteRequest;
    Request acceptedRequest;
    MyDialog myDialog;
    RequestRespondPresenter requestRespondPresenter = new RequestRespondPresenter(this);
    MySearchFriendPresenter mySearchFriendPresenter = new MySearchFriendPresenter(this);
    MyDeclineRequestPresenter myDeclineRequestPresenter = new MyDeclineRequestPresenter(this);
    public FriendRequestAdapter(@NonNull Context context, int resource, @NonNull List<Request> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId=resource;
        this.requests=objects;
        myDialog = new MyDialog((Activity) context);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        if (request.getIsAgreed().equals("0")){
            holder.tvAdded.setVisibility(View.INVISIBLE);
            holder.tvAdded.setClickable(false);
            holder.btnAccept.setVisibility(View.VISIBLE);
        }else {
            holder.tvAdded.setVisibility(View.VISIBLE);
            holder.tvAdded.setClickable(true);
            holder.btnAccept.setVisibility(View.INVISIBLE);
        }
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                acceptedRequest = request;
                myDialog.createLoadingGifDialog();
                requestRespondPresenter.sendRespond(acceptedRequest.getSenderID(),MainActivity.me,acceptedRequest.getSenderNickname());
            }
        });
        if (request.getIsRead().equals("1")){
            holder.tvNew.setVisibility(View.INVISIBLE);
        }else {
            holder.tvNew.setVisibility(View.VISIBLE);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request.getIsAgreed().equals("0")){
                    myDialog.createLoadingGifDialog();
                    myDeclineRequestPresenter.declineRequest(MainActivity.me.getId(),request.getSenderID());
                    deleteRequest = request;
                }else {
                    removeFromRequest(request);
                }

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
                        myDialog.createLoadingGifDialog();
                        mySearchFriendPresenter.attemptSearchFriends(request.getSenderName());
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
        holder.tvUsername.setText(request.getSenderName());
        holder.tvExtras.setText(request.getExtras());
        PicassoClient.downloadImage(context,request.getSenderUrl(),holder.ivRequestProfile);
        return view;
    }


    @Override
    public void respondSuccess(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        myDialog.cancelLoadingGifDialog();
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("friend_id",acceptedRequest.getSenderID());
        values.put("friend_username",acceptedRequest.getSenderName());
        values.put("friend_nickname",acceptedRequest.getSenderNickname());
        values.put("friend_url",acceptedRequest.getSenderUrl());
        values.put("username",MainActivity.me.getUsername());
        database.insert("friend",null,values);
        values.clear();
        values.put("isAgreed","1");
        database.update("request",values,"username=? AND sender=?",
                new String[]{MainActivity.me.getUsername(),acceptedRequest.getSenderName()});
        Intent intent = new Intent(ContactsFragment.NEW_FRIEND_ADDED);
        context.sendBroadcast(intent);

        requests.get(index).setIsAgreed("1");
        notifyDataSetChanged();
    }
    @Override
    public void respondFail(String msg) {
       myDialog.cancelLoadingGifDialog();
        Toast.makeText(getContext(),"Fail to accept",Toast.LENGTH_SHORT).show();
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
            String nickname = object.getString("nickname");
            String whatsup = object.getString("what_is_up");
            String url = object.getString("url");
            int age = object.getInt("age");
            String gender = object.getString("gender");
            String region = object.getString("region");
            int relationshipType = object.getInt("relationship_type");
            User user = new User(user_id,username,nickname,url,gender,region,whatsup,age,relationshipType);
            ProfileActivity_.intent(context).extra("user",user).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void declineSuccess(String msg) {
        myDialog.cancelLoadingGifDialog();
        removeFromRequest(deleteRequest);
    }
    public void removeFromRequest(Request request){
        SQLiteDatabase database = MainActivity.xunChatDatabaseHelper.getWritableDatabase();
        database.delete("request",
                "sender=? AND sender_id=?",
                new String[]{request.getSenderName(),String.valueOf(request.getSenderID())});
        requests.remove(request);
        notifyDataSetChanged();
    }

    @Override
    public void declineFail(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    class ViewHolder{
        @BindView(R.id.iv_request_profile) ImageView ivRequestProfile;
        @BindView(R.id.tv_sender_username) TextView tvUsername;
        @BindView(R.id.tv_sender_extras) TextView tvExtras;
        @BindView(R.id.btn_accept) Button btnAccept;
        @BindView(R.id.tv_added) TextView tvAdded;
        @BindView(R.id.tv_latest_request) TextView tvNew;
        @BindView(R.id.rl_whole_friend_request) RelativeLayout rlFriendRequest;
        @BindView(R.id.tv_request_delete) TextView tvDelete;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
