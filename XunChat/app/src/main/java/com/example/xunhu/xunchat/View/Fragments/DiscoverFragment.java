package com.example.xunhu.xunchat.View.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.Activities.SubActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by xunhu on 6/7/2017.
 */

public class DiscoverFragment extends Fragment {
    @BindView(R.id.ll_moments)
    LinearLayout momentLayout;
    @BindView(R.id.iv_new_post_found)
    ImageView ivNewPostFound;
    @BindView(R.id.llScanQRCode)
    LinearLayout scanQRCodeLayout;
    @BindView(R.id.llPeopleNearby)
    LinearLayout peopleNearbyLayout;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.discover_fragment,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }
    @OnClick({R.id.ll_moments, R.id.iv_new_post_found,R.id.llScanQRCode,R.id.llPeopleNearby})
    public void onResponse(View view){
        switch (view.getId()){
            case R.id.ll_moments:
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("type","moments");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @OnTouch({R.id.ll_moments})
    public boolean onTouchRespond(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                momentLayout.setBackgroundColor(Color.parseColor("#E1E0DE"));
                break;
            case MotionEvent.ACTION_UP:
                momentLayout.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }

        return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
