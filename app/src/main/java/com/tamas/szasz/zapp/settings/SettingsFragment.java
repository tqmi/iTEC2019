package com.tamas.szasz.zapp.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.tamas.szasz.zapp.R;
import com.tamas.szasz.zapp.SettingsActivity;
import com.tamas.szasz.zapp.credentials.User;


public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference mChangeFirstNamePreference;
    private Activity mActivity;
    private Preference mFeedback;
    private Preference mChangeLastNamePreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    private void enablePasswordEmailChange() {
        mChangeFirstNamePreference = findPreference("firstName_change");
        mChangeFirstNamePreference.setSummary("");

    }



    @Override
    public void onResume() {
        super.onResume();

        mActivity = getActivity();
        ((SettingsActivity) mActivity).setActionBarTitle(mActivity.getString(R.string.settings_bar_title));
    }


    private void setPreferencesForCurrentUser() {
        setFirstNamePreference();
        setLastNamePreference();

        setFeedbackPreference();
        setLogOut();
    }


    private void setLogOut() {
        Preference preference = findPreference("logout");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
//                User.getInstance().deleteToken();
                return false;
            }
        });
    }


    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_Settings_FL_Holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setFeedbackPreference() {
        mFeedback = findPreference("help_feedback");
        assert mFeedback != null;
        mFeedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startSendEmailIntent();
                return true;
            }
        });
    }

    private void startSendEmailIntent() {
        Intent _sendFeedback = new Intent(Intent.ACTION_SEND);
        String[] recipients = {"TeamZapp@gmail.com"};
        _sendFeedback.putExtra(Intent.EXTRA_EMAIL, recipients);
        _sendFeedback.setType("text/plain");
        _sendFeedback.setPackage("com.google.android.gm");
        mActivity.startActivity(_sendFeedback);
    }




    private void setFirstNamePreference() {
        mChangeFirstNamePreference = findPreference("firstName_change");
        assert mChangeFirstNamePreference != null;
        mChangeFirstNamePreference.setSummary("");
        mChangeFirstNamePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ChangeNameDialog(mActivity, "", mChangeFirstNamePreference, SettingsFragment.this);
                return true;
            }
        });
    }

    private void setLastNamePreference() {
        mChangeLastNamePreference = findPreference("firstName_change");
        assert mChangeLastNamePreference != null;
        mChangeLastNamePreference.setSummary("");
        mChangeLastNamePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ChangeLastNameDialog(mActivity, "", mChangeLastNamePreference, SettingsFragment.this);
                return true;
            }
        });
    }


}
