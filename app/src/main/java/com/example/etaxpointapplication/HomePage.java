package com.example.etaxpointapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;

public class HomePage extends AppCompatActivity {
private CalendarView calendarView;
private TimePickerDialog open_Timedialog;
private Button from, to;
Dialog dialog;

RecyclerView recyclerView;
Toolbar toolbar;


private String stringDateSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        calendarView = findViewById(R.id.calendar);

        //Meeting list
        recyclerView = (RecyclerView) findViewById(R.id.meetinglist);
         new FirebaseDatabaseHelper().viewMeetings(new FirebaseDatabaseHelper.DataStatus() {
             @Override
             public void DataIsLoaded(List<Meetings> list, List<String> keys) {
                 new MyAdapter().setConfig(recyclerView, HomePage.this, list, keys);
             }



             @Override
             public void DataIsInserted() {

             }

             @Override
             public void DataIsUpdated() {

             }

             @Override
             public void DataIsDeleted() {

             }
         });



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
        Button savebtn= dialog.findViewById(R.id.save);
        ImageButton close=dialog.findViewById(R.id.close);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        DocumentReference newDocRef = usersRef.document();

        String uniqueId = newDocRef.getId();

        TextView meetingid= dialog.findViewById(R.id.IDmeeting);
        EditText title,location, description;

        title=dialog.findViewById(R.id.title);
        location=dialog.findViewById(R.id.location);
        description=dialog.findViewById(R.id.description);
        meetingid.setText(uniqueId);
        from = dialog.findViewById(R.id.fromDate);
        to = dialog.findViewById(R.id.toDate);
        TextView date = dialog.findViewById(R.id.date);

        date.setText(stringDateSelected);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
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
                                to.setText(i + ":" + i1);
                            }
                        }, hourOfDay, minutes, is24HourView);

                open_TimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                open_TimeDialog.show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Meetings");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = user.getUid();
                String title_m = title.getText().toString().trim();
                String description_m = description.getText().toString().trim();
                String location_m = location.getText().toString().trim();
                String todate_m = to.getText().toString().trim();
                String fromdate_m = from.getText().toString().trim();
                String id_m = meetingid.getText().toString().trim();
                String date_m = date.getText().toString().trim();

                // Save the meeting to Firebase
                Meetings meetings = new Meetings(title_m, location_m, description_m, fromdate_m, todate_m, id_m, date_m);
                mDatabase.child(userUid).child(id_m).setValue(meetings).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(HomePage.this, "Meeting saved", Toast.LENGTH_SHORT).show();

                            // Set an alarm for the meeting
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            int hourOfDay = 0;
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            int minutes = 0;
                            int month=0;
                            int dayOfMonth=0;
                            int year=0;
                            calendar.set(Calendar.MINUTE, minutes);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            calendar.set(Calendar.YEAR, year);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(HomePage.this, MeetingAlarmReceiver.class);
                            intent.putExtra("title", title_m);
                            intent.putExtra("location", location_m);
                            intent.putExtra("description", description_m);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePage.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                            dialog.hide();
                        } else {
                            Toast.makeText(HomePage.this, "Meeting not saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
  private boolean isConnectedToInternet(){
      ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
      return networkInfo!= null && networkInfo.isConnected();
  }

    }

