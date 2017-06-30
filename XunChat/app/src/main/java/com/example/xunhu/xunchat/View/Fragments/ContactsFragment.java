package com.example.xunhu.xunchat.View.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.SubActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by xunhu on 6/7/2017.
 */

public class ContactsFragment extends Fragment {
    @BindView(R.id.rlNewFriends)
    RelativeLayout rlNewFriends;
    @BindView(R.id.tv_num_of_request)
    TextView tvNumOfRequests;
    @BindView(R.id.lv_contacts)
    ListView lvContacts;
    private Unbinder unbinder;
    ContactFragmentInterface comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment_layout,container,false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (ContactFragmentInterface) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick({R.id.rlNewFriends})
    public void onRespond(View view){
        switch (view.getId()){
            case R.id.rlNewFriends:
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("type","new friends");
                startActivity(intent);
                comm.clearRequests();
                break;
            default:
                break;
        }
    }
    @OnTouch({R.id.rlNewFriends})
    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                rlNewFriends.setBackgroundColor(Color.parseColor("#E1E0DE"));
                break;
            case MotionEvent.ACTION_UP:
                rlNewFriends.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }

        return false;
    }

    public TextView getTvNumOfRequests() {
        return tvNumOfRequests;
    }
    public interface ContactFragmentInterface{
        void clearRequests();
    }
}
