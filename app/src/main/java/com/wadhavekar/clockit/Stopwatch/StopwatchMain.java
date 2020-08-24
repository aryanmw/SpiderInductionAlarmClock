package com.wadhavekar.clockit.Stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.clockit.Alarm.CreateAlarm;
import com.wadhavekar.clockit.R;
import com.wadhavekar.clockit.Timer.TimerMain;

import java.util.ArrayList;

public class StopwatchMain extends AppCompatActivity {
    Button start,stop,lap;
    Chronometer chronometer;
    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate =0L;
    int sec,min,milliSec;
    TextView lapText;
    ArrayList<TimeFormat> lapTimes;
    RecyclerView rv;
    LinearLayoutManager ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        rv = findViewById(R.id.rv_lap);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        start = findViewById(R.id.button_startChronometer);
        stop = findViewById(R.id.button_stopChronometer);
        chronometer = findViewById(R.id.stopWatch);
        lap = findViewById(R.id.button_sw_lap);

        ll = new LinearLayoutManager(StopwatchMain.this);
        //lapText = findViewById(R.id.lapTime);

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lapText.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+ String.format("%02d",milliSec));
                if (lapTimes == null || lapTimes.size() == 0){
                    lapTimes = new ArrayList<>();
                    TimeFormat tf = new TimeFormat(min,sec,milliSec);
                    lapTimes.add(tf);

                }
                else{
                    long prevTime = lapTimes.get(lapTimes.size()-1).getMilliSec();
                    long listTime = (min*60*1000)+(sec*1000)+(milliSec) ;
                    long currentLap = listTime - prevTime;
                    TimeFormat tf =  new TimeFormat((int) listTime/1000/60,(int)(listTime/1000)%60,(int)listTime%100);
                    lapTimes.add(tf);
                }
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(lapTimes);
                rv.setLayoutManager(ll);
                rv.setAdapter(adapter);


            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(StopwatchMain.this,MyService.class);
                if (!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    start.setText("Pause");
                    stop.setVisibility(View.INVISIBLE);
                    isResume=true;
                    lap.setVisibility(View.VISIBLE);
                }
                else{
                    tBuff+=tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    stop.setVisibility(View.VISIBLE);
                    start.setText("Resume");
                    lap.setVisibility(View.INVISIBLE);
                    isResume=false;
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume){
                    tMilliSec = 0L;
                    tStart = 0L;
                    tUpdate = 0L;
                    tBuff = 0L;
                    sec=0;
                    min=0;
                    milliSec=0;
                    start.setText("Start");
                    lap.setVisibility(View.INVISIBLE);
                    chronometer.setText("00:00:00");
                    lapTimes.clear();
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(lapTimes);
                    rv.setLayoutManager(ll);
                    rv.setAdapter(adapter);

                }
            }
        });

        handler = new Handler();

    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int) (tUpdate%100);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+ String.format("%02d",milliSec));
            handler.postDelayed(this,60);
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_alarm:
                    Intent intent = new Intent(StopwatchMain.this, CreateAlarm.class);
                    startActivity(intent);
                    break;

                case R.id.nav_timer:
                    Intent intent2 = new Intent(StopwatchMain.this, TimerMain.class);
                    startActivity(intent2);
                    break;

            }
            return true;
        }
    };
}

class TimeFormat{
    int min,sec,milliSec;

    public TimeFormat(int min, int sec, int milliSec) {
        this.min = min;
        this.sec = sec;
        this.milliSec = milliSec;
    }

    public String getLapTimeInString(){
        return String.format("%02d",min)+":"+String.format("%02d",sec)+":"+ String.format("%02d",milliSec);

    }

    public long getMilliSec(){
        return (min*60*1000)+(sec*1000)+(milliSec);
    }



}

class ListViewAdapter extends ArrayAdapter<TimeFormat>{
    Context context;
    ArrayList<TimeFormat> myList;

    public ListViewAdapter(@NonNull Context context,  ArrayList<TimeFormat> myList) {
        super(context, R.layout.list_item);
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.list_item,parent,false);
        TextView lapTime = v.findViewById(R.id.tv_lap);


        lapTime.setText(myList.get(position).getLapTimeInString());

        return v;
    }


}
