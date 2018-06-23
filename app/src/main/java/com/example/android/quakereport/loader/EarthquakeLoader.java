package com.example.android.quakereport.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // Required to trigger loadInBackground()
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // Don't perform the request if the url is null.
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchEarthquakeData(mUrl);
    }

}
