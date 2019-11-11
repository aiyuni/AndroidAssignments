package com.example.Lee_Li;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class MonthlyListAdapter extends ArrayAdapter<BloodPressure> {

    private Activity context;
    private List<BloodPressure> readingsList;

    public MonthlyListAdapter(Activity context, List<BloodPressure> readingsList){
        super(context, R.layout.monthly_list, readingsList);
        this.context = context;
        this.readingsList = readingsList;
    }

    public MonthlyListAdapter(Context context, int resource, List<BloodPressure> objects, Activity context1, List<BloodPressure> readingsList){
        super(context, resource, objects);
        this.context = context1;
        this.readingsList = readingsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.monthly_list, null, true);
        TextView userIdView = listViewItem.findViewById(R.id.textViewUserId);
        //TextView readingDateView = listViewItem.findViewById(R.id.textViewReadingDate);
        //TextView readingTimeView = listViewItem.findViewById(R.id.textViewReadingTime);
        TextView systolicView = listViewItem.findViewById(R.id.textViewSystolicReading);
        TextView diastolicView = listViewItem.findViewById(R.id.textViewDiastolicReading);
        TextView conditionView = listViewItem.findViewById(R.id.textViewCondition);

        BloodPressure bp = readingsList.get(position);
        userIdView.setText(String.valueOf(bp.userName));
        //readingDateView.setText("Date Of Reading: " + bp.readingDate);
        //readingTimeView.setText("Time Of Reading: " + bp.readingTime);
        systolicView.setText("Systolic Reading: " + String.valueOf(bp.systolicReading));
        diastolicView.setText("Diastolic Reading: " + String.valueOf(bp.diastolicReading));
        conditionView.setText("Condition: " + String.valueOf(bp.condition));

        return listViewItem;
    }

}
