package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReadingsActivity extends AppCompatActivity {

    public ListView lvReadings;
    public List<BloodPressure> readingsList;

    DatabaseReference databaseReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        databaseReadings = FirebaseDatabase.getInstance().getReference("readings");

        lvReadings = findViewById(R.id.lvReadings);
        readingsList = new ArrayList<BloodPressure>();

    }

    @Override
    protected void onStart(){
        super.onStart();
        databaseReadings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readingsList.clear();
                for(DataSnapshot readingsSnapShot: dataSnapshot.getChildren()){
                    BloodPressure bp = readingsSnapShot.getValue(BloodPressure.class);
                    readingsList.add(bp);
                }

                ReadingsListAdapter adapter = new ReadingsListAdapter(ReadingsActivity.this, readingsList);
                lvReadings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
