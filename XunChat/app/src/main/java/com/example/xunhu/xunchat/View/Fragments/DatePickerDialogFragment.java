package com.example.xunhu.xunchat.View.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.xunhu.xunchat.R;

import java.util.Date;

/**
 * Created by xunhu on 6/16/2017.
 */

public class DatePickerDialogFragment extends DialogFragment {
    DatePickerInterface comm;
    DatePicker datePicker;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (DatePickerInterface) activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.date_picker_layout,null);
        datePicker = (DatePicker) v.findViewById(R.id.dp);
        datePicker.setMaxDate(new Date().getTime());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("select your date of birth").setView(v).setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String d = String.valueOf(datePicker.getDayOfMonth());
                String m = String .valueOf(datePicker.getMonth()+1);
                String y = String.valueOf(datePicker.getYear());
                String birthday = d+"/"+m+"/"+y;
                comm.birthdaySelected(birthday);
            }
        }).setNegativeButton("Cancel",null);
        return builder.create();
    }
    public interface DatePickerInterface{
        void birthdaySelected(String birthday);
    }
}
