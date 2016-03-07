package com.zhzane.android.dotnoteandroid.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.R;

/**
 * Created by KWENS on 2016/3/7.
 */
public class UserEditDialogFragment extends DialogFragment {

    private EditText userEdit;
    private DBManager mgr;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_user_edit, null);
        userEdit = (EditText) view.findViewById(R.id.user_edit_editText_username);

        builder.setView(view)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserEditListener listener = (UserEditListener)getActivity();
                                listener.onUserEditComplete(userEdit.getText().toString());
                            }
                        }
                )
                .setNegativeButton("NO", null);
        return builder.create();
    }

    public interface UserEditListener {
        void onUserEditComplete(String userName);
    }
}
