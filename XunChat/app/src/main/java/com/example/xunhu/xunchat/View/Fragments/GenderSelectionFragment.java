package com.example.xunhu.xunchat.View.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by xunhu on 6/13/2017.
 */

public class GenderSelectionFragment extends DialogFragment {
    final String[] items = new String[]{"Female", "Male"};
    String selection = "Female";
    GenderInterface comm;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (GenderInterface) activity;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select your gender").setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        selection = items[which];
                        break;
                    case 1:
                        selection = items[which];
                        break;
                    default:
                        break;
                }
            }
        }).setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ok is clicked
                comm.respondGender(selection);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
    public interface GenderInterface{
        void respondGender(String selection);
    }
}
