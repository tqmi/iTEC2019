package com.tamas.szasz.zapp.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.preference.Preference;

import com.tamas.szasz.zapp.R;


public class ChangeNameDialog {
    private Activity mActivity;
    private EditText mDisplayNameEditText;
    private String mDisplayName;
    private Preference mPreference;
    private SettingsFragment mSettingsFragment;
    private Context mContext;

    public ChangeNameDialog(Context context, Activity activity, String displayName, Preference preference, SettingsFragment fragment) {
        mActivity = activity;
        mDisplayName = displayName;
        mPreference = preference;
        mSettingsFragment = fragment;
        this.mContext = context;
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View _dialogLayout = inflater.inflate(R.layout.dialog_settings_first_name, null);
        builder.setView(_dialogLayout);
        builder.setTitle(mActivity.getString(R.string.dialog_display_first_name_title));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String _name = mDisplayNameEditText.getText().toString();
                if(validName(_name)) {
                    saveDisplayName(_name);
                }
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog _dialog = builder.create();
        _dialog.show();
        mDisplayNameEditText = _dialogLayout.findViewById(R.id.dialog_settings_first_name);
        mDisplayNameEditText.setHint(mDisplayName);
    }

    private boolean validName(String name) {
        if(name.length() > 28 || name.length() < 1) {
            mDisplayNameEditText.setError(mActivity.getString(R.string.dialog_display_name_validity));
            return false;
        }
        return true;
    }

    private void saveDisplayName(final String newDisplayName) {

    }
}
