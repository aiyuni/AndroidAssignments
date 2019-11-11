package com.example.assignment2;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AverageActivity extends AppCompatActivity {

    public ListView lvReadings;
    public List<BloodPressure> averagesList;

    DatabaseReference databaseReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average);

        databaseReadings = FirebaseDatabase.getInstance().getReference("readings");

        lvReadings = findViewById(R.id.lvReadings);
        averagesList = new ArrayList<BloodPressure>();

    }

    @Override
    protected void onStart(){
        super.onStart();
        databaseReadings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> namesSet = new HashSet<String>();
                for (DataSnapshot averageSnapShot: dataSnapshot.getChildren()){
                    BloodPressure bp = averageSnapShot.getValue(BloodPressure.class);
                    namesSet.add(bp.userName);
                }

                averagesList.clear();

                ArrayList<String> namesList = new ArrayList<String>(namesSet);
                ArrayList<BloodPressureAverages> bpAvgList  = new ArrayList<BloodPressureAverages>();
                for (int i = 0; i < namesSet.size(); i++){
                    int sumSystolicReading = 0;
                    int sumDiastolicReading = 0;
                    int count = 0;
                    for (DataSnapshot averageSnapShot: dataSnapshot.getChildren()){
                        BloodPressure bp = averageSnapShot.getValue(BloodPressure.class);
                        if (bp.userName != null && bp.userName.equals(namesList.get(i))){
                            sumSystolicReading += bp.systolicReading;
                            sumDiastolicReading += bp.diastolicReading;
                            count++;
                        }
                    }
                    BloodPressureAverages bpAvg = new BloodPressureAverages(namesList.get(i), sumSystolicReading/count, sumDiastolicReading/count);
                    bpAvgList.add(bpAvg);
                    System.out.println("bp avg is: " + bpAvg.toString());
                }
                AveragesListAdapter adapter = new AveragesListAdapter(AverageActivity.this, bpAvgList);
                lvReadings.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
