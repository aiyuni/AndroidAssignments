package com.example.Lee_Li;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editUserName;
    TextView editReadingDate;
    TextView editReadingTime;
    EditText editSystolicReading;
    EditText editDiastolicReading;
    TextView condition;
    Button buttonAddReading;
    Button buttonGoToReadings;
    Button buttonGoToAverages;

    DatabaseReference databaseReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = findViewById(R.id.editTextUserName);
        editReadingDate = findViewById(R.id.editTextReadingDate);
        editReadingTime = findViewById(R.id.editTextReadingTime);
        editSystolicReading = findViewById(R.id.editTextSystolicReading);
        editDiastolicReading = findViewById(R.id.editTextDiastolicReading);
        condition = findViewById(R.id.editTextCondition);
        condition.setInputType(InputType.TYPE_NULL);  //read only
        condition.setFocusable(false);

        buttonAddReading = findViewById(R.id.buttonAddReading);
        buttonGoToReadings = findViewById(R.id.buttonGoToReadings);
        buttonGoToAverages = findViewById(R.id.buttonGoToAverages);

        databaseReadings = FirebaseDatabase.getInstance().getReference("readings");

        buttonAddReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReading();
            }
        });

        buttonGoToReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ReadingsActivity.class);
                startActivity(intent);
            }
        });

        buttonGoToAverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MonthlyActivity.class);
                startActivity(intent);
            }
        });

        resetData();

    }

    private void addReading(){
        //String userId = editUserName.getText().toString().trim();
        String userName = editUserName.getText().toString();
        String dateString = editReadingDate.getText().toString();
        String timeString = editReadingTime.getText().toString();
        String systolicReadingString = editSystolicReading.getText().toString();
        String diastolicReadingString = editDiastolicReading.getText().toString();

        String userId = databaseReadings.push().getKey();
        BloodPressure bp = new BloodPressure(userId, userName, dateString, timeString, Integer.parseInt(systolicReadingString), Integer.parseInt(diastolicReadingString));

        Task setValueTask =databaseReadings.child(userId).setValue(bp);

        if (Integer.parseInt(systolicReadingString) > 180 || Integer.parseInt(diastolicReadingString) > 120){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("WARNING");
                    alertDialog.setMessage("You are hypertensive. See doctor immediately!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                setValueTask.addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(MainActivity.this, "Blood pressure reading added", Toast.LENGTH_LONG).show();
                        editUserName.setText("");
                        editReadingDate.setText("");
                editReadingTime.setText("");
                editDiastolicReading.setText("");
                editSystolicReading.setText("");
                resetData();

            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "something went wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void resetData() {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        try {
            editReadingDate.setText(dateFormatter.format(new Date()).toString());
            editReadingTime.setText(timeFormatter.format(new Date()).toString());
        } catch (Exception e){
            System.out.println("something went wrong: " + e.toString());
        }
        condition.setText("Please add the reading to see your condition");
    }
}
