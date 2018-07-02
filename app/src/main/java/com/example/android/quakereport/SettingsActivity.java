package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final Preference.OnPreferenceChangeListener sOnPreferenceChangeListener;

    static {
        sOnPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                setPreferenceSummary(preference, stringValue);
                return true;
            }
        };
    }

    private static void setPreferenceSummary(Preference preference, String preferenceString) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(preferenceString);
            if (prefIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[prefIndex]);
            }
        } else {
            preference.setSummary(preferenceString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            List<Preference> preferences = new ArrayList<>();
            preferences.add(findPreference(getString(R.string.settings_min_magnitude_key)));
            preferences.add(findPreference(getString(R.string.settings_order_by_key)));

            setPreferencesSummary(preferences);
        }

        private void setPreferencesSummary(List<Preference> preferences) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preferences.get(0).getContext());

            for (Preference preference : preferences) {
                String preferenceString = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, preferenceString);
                preference.setOnPreferenceChangeListener(sOnPreferenceChangeListener);
            }
        }
    }

}
