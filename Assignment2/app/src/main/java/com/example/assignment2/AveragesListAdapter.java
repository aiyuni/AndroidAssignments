package com.example.assignment2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;

public class AveragesListAdapter extends ArrayAdapter<BloodPressureAverages> {
    private Activity context;
    private List<BloodPressureAverages> readingsList;

    public AveragesListAdapter(Activity context, List<BloodPressureAverages> readingsList){
        super(context, R.layout.list_averages, readingsList);
        this.context = context;
        this.readingsList = readingsList;
    }

    public AveragesListAdapter(Context context, int resource, List<BloodPressureAverages> objects, Activity context1, List<BloodPressureAverages> readingsList){
        super(context, resource, objects);
        this.context = context1;
        this.readingsList = readingsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_averages, null, true);
        TextView userIdView = listViewItem.findViewById(R.id.textViewUserId);
        TextView systolicView = listViewItem.findViewById(R.id.textViewSystolicReading);
        TextView diastolicView = listViewItem.findViewById(R.id.textViewDiastolicReading);
        TextView monthView = listViewItem.findViewById(R.id.textViewMonth);
        TextView condition = listViewItem.findViewById(R.id.textViewCondition);

        BloodPressureAverages bp = readingsList.get(position);
        userIdView.setText(String.valueOf(bp.userName));
        systolicView.setText("Systolic Reading: " + String.valueOf(bp.systolicReadingTotal));
        diastolicView.setText("Diastolic Reading: " + String.valueOf(bp.diastolicReadingTotal));
        monthView.setText("Month-to-date average readings for Nov 2019");
        condition.setText("Average Condition: " + String.valueOf(bp.condition));


        return listViewItem;
}

}
