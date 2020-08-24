package com.wadhavekar.clockit.Alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wadhavekar.clockit.R;

public class AlarmMain extends AppCompatActivity {
    Button createAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);

        createAlarm = findViewById(R.id.create_alarm);

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmMain.this,CreateAlarm.class);
                startActivity(intent);
            }
        });
    }
}
