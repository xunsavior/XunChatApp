package com.example.xunhu.xunchat.View.AllAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.xunhu.xunchat.Model.Entities.Message;

import java.util.List;

/**
 * Created by xunhu on 7/18/2017.
 */

public class ChatMessageAdapter extends ArrayAdapter<Message> {

    public ChatMessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }
}
