package com.example.xunhu.xunchat.View.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.xunhu.xunchat.R;

/**
 * Created by xunhu on 7/14/2017.
 */

@SuppressLint("ValidFragment")
public class RemarkDialogFragment extends DialogFragment {
    String remark;
    EditText etRemark;
    RemarkDialogFragmentInterface comm;
    public RemarkDialogFragment(String remark){
        this.remark=remark;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remark_dialog_layout,null);
        etRemark = (EditText) view.findViewById(R.id.etRespondRemark);
        etRemark.setText(remark);
        builder.setView(view).setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remark = etRemark.getText().toString();
                comm.setRemark(remark);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm= (RemarkDialogFragmentInterface) activity;
    }

    public interface RemarkDialogFragmentInterface{
        void setRemark(String remark);
    }
}
