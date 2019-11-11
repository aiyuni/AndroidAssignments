package com.example.Lee_Li;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonthlyActivity extends AppCompatActivity {

    public ListView lvReadings;
    public List<BloodPressure> readingsList;

    DatabaseReference databaseReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);

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
                double total = 0.0;
                double count = 0.0;
                double average = 0.0;
                for(DataSnapshot readingsSnapShot: dataSnapshot.getChildren()){
                    double systolic = Double.parseDouble((String)readingsSnapShot.child("systolicReading").getValue());
                    double diastolic = Double.parseDouble((String)readingsSnapShot.child("diastolicReading").getValue());
                    BloodPressure bp = readingsSnapShot.getValue(BloodPressure.class);
                    readingsList.add(bp);
                }

                MonthlyListAdapter adapter = new MonthlyListAdapter(MonthlyActivity.this, readingsList);
                lvReadings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void showUpdateDialog(final String userId, String userName, String readingDate, String readingTime, int systolicReading, int diastolicReading){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        //final EditText editTextUserId = dialogView.findViewById(R.id.editTextUserId);
//        final EditText editTextUserName = dialogView.findViewById(R.id.editTextUserName);
//        final EditText editTextReadingDate = dialogView.findViewById(R.id.editTextReadingDate);
//        final EditText editTextReadingTime = dialogView.findViewById(R.id.editTextReadingTime);
//        final EditText editTextSystolicReading = dialogView.findViewById(R.id.editTextSystolicReading);
//        final EditText editTextDiastolicReading = dialogView.findViewById(R.id.editTextDiastolicReading);
//        final EditText editTextCondition = dialogView.findViewById(R.id.editTextCondition);
//
//        // editTextUserId.setText(userId);
//        editTextUserName.setText(userName);
//        editTextReadingDate.setText(readingDate);
//        editTextReadingTime.setText(readingTime);
//        editTextSystolicReading.setText(String.valueOf(systolicReading));
//        editTextDiastolicReading.setText(String.valueOf(diastolicReading));
//
//        // editTextUserId.setFocusable(false);
//        editTextReadingDate.setFocusable(false);
//        editTextReadingTime.setFocusable(false);
//
//        final Button updateButton = dialogView.findViewById(R.id.btnUpdate);
//        final Button deleteButton = dialogView.findViewById(R.id.btnDelete);
//
//        dialogBuilder.setTitle("Update reading");
//
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//    }

}
