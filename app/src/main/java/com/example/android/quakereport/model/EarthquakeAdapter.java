package com.example.android.quakereport.model;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hjalmar
 * On 22/04/2018.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final DecimalFormat DECIMAL_FORMATTER;
    private static final String PLACE_REGEX;
    private static final String PLACE_SPLIT;

    private final LayoutInflater mInflater = LayoutInflater.from(getContext());

    static {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("0.0", decimalFormatSymbols);
        PLACE_REGEX = "^\\d+km\\s[NSEW]+\\sof.*";
        PLACE_SPLIT = "of";
    }

    private static class ViewHolder {
        TextView magnitude;
        TextView locationOffset;
        TextView primaryLocation;
        TextView date;
        TextView time;
    }

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> earthquakes) {
        // Because this is a custom adapter, it is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.earthquake_item, parent, false);

            holder = new ViewHolder();
            holder.magnitude = convertView.findViewById(R.id.magnitude);
            holder.locationOffset = convertView.findViewById(R.id.location_offset);
            holder.primaryLocation = convertView.findViewById(R.id.primary_location);
            holder.date = convertView.findViewById(R.id.date);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Earthquake earthquake = getItem(position);
        if (earthquake == null) {
            return convertView;
        }

        holder.magnitude.setText(DECIMAL_FORMATTER.format(earthquake.getMagnitude()));
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String locationOffsetText;
        String primaryLocationText;
        if (earthquake.getPlace().matches(PLACE_REGEX)) {
            int index = earthquake.getPlace().indexOf(PLACE_SPLIT);
            locationOffsetText = earthquake.getPlace().substring(0, index + 2);
            primaryLocationText = earthquake.getPlace().substring(index + 3, earthquake.getPlace().length());
        } else {
            locationOffsetText = getContext().getString(R.string.near_the);
            primaryLocationText = earthquake.getPlace();
        }

        holder.locationOffset.setText(locationOffsetText);
        holder.primaryLocation.setText(primaryLocationText);

        Date dateAndTime = new Date(earthquake.getTimems());
        holder.date.setText(getFormattedDate(dateAndTime));
        holder.time.setText(getFormattedTime(dateAndTime));

        return convertView;
    }

    /**
     * Return the appropriate alert color
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int floor = (int) Math.floor(magnitude);
        switch (floor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "Jan 7, 1985") from a Date object.
     */
    private String getFormattedDate(Date dateAndTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return dateFormatter.format(dateAndTime);
    }

    /**
     * Return the formatted time string (i.e. "05:15 PM") from a Date object.
     */
    private String getFormattedTime(Date dateAndTime) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
        return timeFormatter.format(dateAndTime);
    }

}
