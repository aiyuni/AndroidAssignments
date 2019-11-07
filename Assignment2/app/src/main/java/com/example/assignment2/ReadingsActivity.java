package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

        lvReadings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                BloodPressure bp = readingsList.get(i);
                showUpdateDialog(bp.userID, bp.readingDate, bp.readingTime, bp.systolicReading, bp.diastolicReading);
                return false;
            }
        });

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

    private void showUpdateDialog(final String userId, String readingDate, String readingTime, int systolicReading, int diastolicReading){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextUserId = dialogView.findViewById(R.id.editTextUserId);
        final EditText editTextReadingDate = dialogView.findViewById(R.id.editTextReadingDate);
        final EditText editTextReadingTime = dialogView.findViewById(R.id.editTextReadingTime);
        final EditText editTextSystolicReading = dialogView.findViewById(R.id.editTextSystolicReading);
        final EditText editTextDiastolicReading = dialogView.findViewById(R.id.editTextDiastolicReading);
        final EditText editTextCondition = dialogView.findViewById(R.id.editTextCondition);

        editTextUserId.setText(userId);
        editTextReadingDate.setText(readingDate);
        editTextReadingTime.setText(readingTime);
        editTextSystolicReading.setText(String.valueOf(systolicReading));
        editTextDiastolicReading.setText(String.valueOf(diastolicReading));

        editTextUserId.setFocusable(false);

        final Button updateButton = dialogView.findViewById(R.id.btnUpdate);
        final Button deleteButton = dialogView.findViewById(R.id.btnDelete);

        dialogBuilder.setTitle("Update reading");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String readingTime = editTextReadingTime.getText().toString();
                String readingDate = editTextReadingDate.getText().toString();
                String systolicReading = editTextSystolicReading.getText().toString();
                String diastolicReading = editTextDiastolicReading.getText().toString();
               // String condition = editTextCondition.getText().toString();

                updateReadings(userId, readingDate, readingTime, Integer.parseInt(systolicReading), Integer.parseInt(diastolicReading));
                alertDialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteReading(userId);
                alertDialog.dismiss();
            }
        });
    }

    private void updateReadings(String id, String readingDate, String readingTime, int systolicReading, int diastolicReading){

        DatabaseReference dbRef = databaseReadings.child(id);
        BloodPressure bp = new BloodPressure(id, readingDate, readingTime, systolicReading, diastolicReading);

        Task setValueTask = dbRef.setValue(bp);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(ReadingsActivity.this, "Blood Pressure Reading updated.", Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReadingsActivity.this, "Something went wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteReading(String id){
        DatabaseReference dbRef = databaseReadings.child(id);

        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(ReadingsActivity.this, "Blood Pressure Reading deleted.", Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReadingsActivity.this, "Something went wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
