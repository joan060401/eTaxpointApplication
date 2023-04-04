package com.example.etaxpointapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
private CalendarView calendarView;

Dialog dialog;

private String stringDateSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        calendarView = findViewById(R.id.calendar);
        dialog= new Dialog(this);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,  int year, int month, int dayOfMonth) {
                stringDateSelected =Integer.toString(month +1) + "/" +Integer.toString(dayOfMonth) + "/"+ Integer.toString(year);
                calendarclicked();
            }
        });
    }

    private void calendarclicked(){
       // Intent intent = new Intent(HomePage.this, EmployeesAccount.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // startActivity(intent);

        dialog.setContentView(R.layout.activity_new_sched);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView date= dialog.findViewById(R.id.date);
        date.setText(stringDateSelected);

        dialog.show();

    }

}