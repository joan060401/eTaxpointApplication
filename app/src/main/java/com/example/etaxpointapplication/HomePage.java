package com.example.etaxpointapplication;

import static android.R.layout.simple_spinner_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {
private CalendarView calendarView;
private TimePickerDialog open_Timedialog;
private Button from, to;
Dialog dialog;


private String stringDateSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        calendarView = findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,  int year, int month, int dayOfMonth) {
                stringDateSelected =Integer.toString(month +1) + "/" +Integer.toString(dayOfMonth) + "/"+ Integer.toString(year);
                calendarclicked();
            }
        });


    }

    private void calendarclicked() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_new_sched);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        DocumentReference newDocRef = usersRef.document();
        String uniqueId = newDocRef.getId();

        TextView meetingid= dialog.findViewById(R.id.IDmeeting);
        meetingid.setText(uniqueId);
        from = dialog.findViewById(R.id.fromDate);
        to = dialog.findViewById(R.id.toDate);
        TextView date = dialog.findViewById(R.id.date);

        date.setText(stringDateSelected);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hourOfDay = 23;
                int minutes = 55;
                boolean is24HourView = true;

                TimePickerDialog open_TimeDialog = new TimePickerDialog(HomePage.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                from.setText(i + ":" + i1);
                            }
                        }, hourOfDay, minutes, is24HourView);

                open_TimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                open_TimeDialog.show();
            }
        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hourOfDay = 23;
                int minutes = 55;
                boolean is24HourView = true;

                TimePickerDialog open_TimeDialog = new TimePickerDialog(HomePage.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                from.setText(i + ":" + i1);
                            }
                        }, hourOfDay, minutes, is24HourView);

                open_TimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                open_TimeDialog.show();
            }
        });

        dialog.show();
    }
    }

