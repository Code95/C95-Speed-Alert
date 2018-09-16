package com.code95.speedalert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.support.v4.util.Preconditions.checkNotNull;

public class SharedPrefRepository implements SharedPrefDataSource {

    private SharedPreferences mSharedPref;

    @SuppressLint("RestrictedApi")
    public SharedPrefRepository(Context context) {
        checkNotNull(context);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setLastAlertDate(Long date) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putLong(SharedPrefConstants.PREF_LAST_AlERT_TIME, date);
        editor.apply();

    }

    @Override
    public Long getLastAlertDate() {
        return mSharedPref.getLong(SharedPrefConstants.PREF_LAST_AlERT_TIME, 0);
    }
}
