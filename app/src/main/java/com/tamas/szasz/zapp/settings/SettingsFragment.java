package com.tamas.szasz.zapp.settings;

import android.app.Activity;
import android.content.Context;
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
import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationsUpdater;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.LoginActivity;
import com.tamas.szasz.zapp.main.fragments.CarsFragment;


public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference mChangeFirstNamePreference;
    private Activity mActivity;
    private Preference mFeedback;
    private Preference mChangeLastNamePreference;
    private Preference mMyCarsPreference;
    private String lastName, firstName;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        lastName = User.getInstance().getLastName();
        firstName = User.getInstance().getFirstName();

        mActivity = getActivity();
        ((SettingsActivity) mActivity).setActionBarTitle(mActivity.getString(R.string.settings_bar_title));
        setPreferencesForCurrentUser();
    }


    private void setPreferencesForCurrentUser() {
        setFirstNamePreference();
        setLastNamePreference();
        setMyCarsPreference();
        setFeedbackPreference();
        setLogOut();
    }

    private void setMyCarsPreference() {
        mMyCarsPreference = findPreference("my_cars");
        mMyCarsPreference.setFragment("com.tamas.szasz.zapp.main.fragments.CarsFragment");
        mMyCarsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startFragment(new CarsFragment());
                return true;
            }
        });
    }


    private void setLogOut() {
        final Context context = getContext();
        Preference preference = findPreference("logout");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                StationsUpdater.getInstance().closeThread();
                User.getInstance().deleteToken();
                getActivity().finishAffinity();
                Intent loginIntent = new Intent(context, LoginActivity.class);
                startActivity(loginIntent);
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
        mChangeFirstNamePreference = findPreference("first_name_change");
        assert mChangeFirstNamePreference != null;
        mChangeFirstNamePreference.setSummary(firstName);
        mChangeFirstNamePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ChangeNameDialog(mActivity, firstName, mChangeFirstNamePreference, SettingsFragment.this);
                return true;
            }
        });
    }

    private void setLastNamePreference() {
        mChangeLastNamePreference = findPreference("last_name_change");
        assert mChangeLastNamePreference != null;
        mChangeLastNamePreference.setSummary(lastName);
        mChangeLastNamePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ChangeLastNameDialog(mActivity, lastName, mChangeLastNamePreference, SettingsFragment.this);
                return true;
            }
        });
    }


}
