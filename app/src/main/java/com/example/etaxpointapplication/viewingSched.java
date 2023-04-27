package com.example.etaxpointapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class viewingSched extends AppCompatActivity {

    EditText titlev,locationv,descriptionv;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_sched);
        titlev=findViewById(R.id.titleV);
        locationv=findViewById(R.id.locationV);
        descriptionv= findViewById(R.id.descriptionV);
        id= findViewById(R.id.IDmeetingV);


    }
}