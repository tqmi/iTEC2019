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
import com.tamas.szasz.zapp.login.retrofit_threads.user.UpdateThread;

public class ChangeLastNameDialog {
    private Activity mActivity;
    private EditText mDisplayNameEditText;
    private String mDisplayName;
    private Preference mPreference;
    private SettingsFragment mSettingsFragment;
    private  AlertDialog dialog;
    private Context mContext;

    public ChangeLastNameDialog(Context context, Activity activity, String displayName, Preference preference, SettingsFragment fragment) {
        mActivity = activity;
        mDisplayName = displayName;
        mPreference = preference;
        mSettingsFragment = fragment;
        mContext = context;
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View _dialogLayout = inflater.inflate(R.layout.dialog_settings_last_name, null);
        builder.setView(_dialogLayout);
        builder.setTitle(mActivity.getString(R.string.dialog_display_last_name_title));
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
        dialog = builder.create();
        dialog.show();
        mDisplayNameEditText = _dialogLayout.findViewById(R.id.dialog_settings_last_name);
        mDisplayNameEditText.setHint(mDisplayName);
    }

    private boolean validName(String name) {
        if(name.length() > 28 || name.length() < 1) {
            mDisplayNameEditText.setError(mActivity.getString(R.string.dialog_last_name_validity));
            return false;
        }
        return true;
    }

    private void saveDisplayName(final String newLastName) {
        UpdateThread updateThread = new UpdateThread(mContext);
        updateThread.setLastName(newLastName);
        updateThread.run();
        try {
            updateThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
