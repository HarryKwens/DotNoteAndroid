package com.zhzane.android.dotnoteandroid.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zhzane.android.dotnoteandroid.R;

/**
 * Created by KWENS on 2016/5/5.
 */
public class BillDelDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_bill_del, null);
        builder.setView(view)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BillDelListener listener = (BillDelListener) getActivity();
                                listener.onDelBill();
                            }
                        }
                )
                .setNegativeButton("NO", null);
        return builder.create();
    }

    public interface BillDelListener{
        void onDelBill();
    }
}
