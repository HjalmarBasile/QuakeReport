package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

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

            Preference minMagnitudePref = findPreference(getString(R.string.settings_min_magnitude_key));
            setPreferenceSummary(minMagnitudePref);

            minMagnitudePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String stringValue = newValue.toString();
                    preference.setSummary(stringValue);
                    return true;
                }

            });
        }

        private void setPreferenceSummary(Preference preference) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            preference.setSummary(preferenceString);
        }

    }

}
