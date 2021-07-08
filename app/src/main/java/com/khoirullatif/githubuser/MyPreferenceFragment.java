package com.khoirullatif.githubuser;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    private String REMINDER;
    private AlarmReceiver alarmReceiver;
    private SwitchPreference isReminderOn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        init();
        setSummaries();

        alarmReceiver = new AlarmReceiver();
    }

    private void init() {
        REMINDER = getResources().getString(R.string.key_reminder);
        isReminderOn = findPreference(REMINDER);
    }

    private void setSummaries() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        isReminderOn.setChecked(sharedPreferences.getBoolean(REMINDER, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(REMINDER)) {
            isReminderOn.setChecked(sharedPreferences.getBoolean(REMINDER, false));

            if (sharedPreferences.getBoolean(REMINDER, false)) {
                String repeatTime = "09:00";
                String repeatMessage = getString(R.string.repeat_message);
                alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage);
            } else {
                alarmReceiver.cancelAlarm(getContext());
            }

        }
    }
}
