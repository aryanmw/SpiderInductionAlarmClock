package com.wadhavekar.clockit.Alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.clockit.R;
import com.wadhavekar.clockit.Stopwatch.StopwatchMain;
import com.wadhavekar.clockit.Timer.TimerMain;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class CreateAlarm extends AppCompatActivity{
    NumberPicker hours,mins;
    Button setAlarm;
    TextView setTime,tv_question;
    EditText answer;
    CardView mon,tue,wed,thurs,fri,sat,sun,monIn,tueIn,wedIn,thursIn,friIn,satIn,sunIn,default_cv,school;
    ArrayList<Integer> daysOfWeek;
    Intent intent;

    int answerToQsn = 0;
    private static CreateAlarm inst;
    private static final String SHARED_PREF = "Clock It";
    private static final String REQ_CODE = "Request Code";
    int req_code;
    Button stopAlarm;
    int rawTone = 1;
    MathQuestion mq;
    RelativeLayout rl;

    boolean isAlarmRinging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mq = new MathQuestion();
        rl = findViewById(R.id.rl_qsn);
        tv_question = findViewById(R.id.tv_question);
        answer = findViewById(R.id.et_answer);

        stopAlarm = findViewById(R.id.button_enter);
        default_cv = findViewById(R.id.cv_default);
        school = findViewById(R.id.cv_school);
        hours = findViewById(R.id.np_hours);
        mins = findViewById(R.id.np_mins);
        setTime = findViewById(R.id.tv_setTime);
        mon = findViewById(R.id.cv_mon);
        tue = findViewById(R.id.cv_tue);
        wed = findViewById(R.id.cv_wed);
        thurs = findViewById(R.id.cv_thu);
        fri = findViewById(R.id.cv_fri);
        sat = findViewById(R.id.cv_sat);
        sun = findViewById(R.id.cv_sun);

        monIn = findViewById(R.id.cv_monIn);
        tueIn = findViewById(R.id.cv_tueIn);
        wedIn = findViewById(R.id.cv_wedIn);
        thursIn = findViewById(R.id.cv_thuIn);
        friIn = findViewById(R.id.cv_friIn);
        satIn = findViewById(R.id.cv_satIn);
        sunIn = findViewById(R.id.cv_sunIn);


        SharedPreferences sp = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);


        req_code = sp.getInt(REQ_CODE,0);
        req_code++;
        saveDataToSharedPreference(req_code);




        hours.setMaxValue(23);
        hours.setMinValue(0);
        mins.setMaxValue(59);
        mins.setMinValue(0);

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    MyMedia m = new MyMedia();
                    m.stopPlayer();
                    cancelAlarm();
                    stopAlarm.setVisibility(View.INVISIBLE);

            }
        });

        if (daysOfWeek == null){
            daysOfWeek = new ArrayList<Integer>();
        }

        setAlarm = findViewById(R.id.set_button);
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hours.getValue());
                c.set(Calendar.MINUTE,mins.getValue());
                c.set(Calendar.SECOND,0);

                setTimeToText(c);
                startAlarm(c);
                LocalDate currentdate = LocalDate.now();

                java.util.Date utilDate = new java.util.Date();
                java.sql.Date dt = new java.sql.Date(utilDate.getTime());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dt);
                Intent i = new Intent(CreateAlarm.this,AlarmMain.class);
                i.putExtra("AlarmTime",hours.getValue()+" : "+mins.getValue());
                //Toast.makeText(CreateAlarm.this, ""+cal.get(Calendar.DAY_OF_WEEK), Toast.LENGTH_SHORT).show();
                stopAlarm.setVisibility(View.VISIBLE);
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(1)){
                    Integer day = 1;
                    daysOfWeek.remove(day);
                    monIn.setCardBackgroundColor(Color.parseColor("#202125"));

                }
                else{
                    daysOfWeek.add(1);
                    monIn.setCardBackgroundColor(Color.parseColor("#C99806"));

                }
                setAlarm.setVisibility(View.VISIBLE);

            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(2)){
                    Integer day = 2;
                    daysOfWeek.remove(day);
                    tueIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(2);
                    tueIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(3)){
                    Integer day = 3;
                    daysOfWeek.remove(day);
                    wedIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(3);
                    wedIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(4)){
                    Integer day = 4;
                    daysOfWeek.remove(day);
                    thursIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(4);
                    thursIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(5)){
                    Integer day = 5;
                    daysOfWeek.remove(day);
                    friIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(5);
                    friIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(6)){
                    Integer day = 6;
                    daysOfWeek.remove(day);
                    satIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(6);
                    satIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysOfWeek.contains(7)){
                    Integer day = 7;
                    daysOfWeek.remove(day);
                    sunIn.setCardBackgroundColor(Color.parseColor("#202125"));
                }
                else{
                    daysOfWeek.add(7);
                    sunIn.setCardBackgroundColor(Color.parseColor("#C99806"));
                }

                setAlarm.setVisibility(View.VISIBLE);
            }
        });

        default_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rawTone = 1;
                default_cv.setCardBackgroundColor(Color.parseColor("#C99806"));
                school.setCardBackgroundColor(Color.parseColor("#36373B"));
            }
        });

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rawTone = 2;
                school.setCardBackgroundColor(Color.parseColor("#C99806"));
                default_cv.setCardBackgroundColor(Color.parseColor("#36373B"));
            }
        });
    }

    private void setTimeToText(Calendar c){
        String timeText = "Alarm Set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        setTime.setText(timeText);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("key",daysOfWeek);
        intent.putExtra("rawVal",rawTone);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,req_code,intent,0);

        if (daysOfWeek.size() == 0){
            setAlarm.setVisibility(View.INVISIBLE);
        }

        else{
            Toast.makeText(this, ""+daysOfWeek.size(), Toast.LENGTH_SHORT).show();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()-2000,AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("key",daysOfWeek);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
    }

    public ArrayList<Integer> getDaysOfWeek(){
        return daysOfWeek;
    }

    public void saveDataToSharedPreference(int i){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(REQ_CODE , i);

        editor.apply();
        Log.i("SP","??????????"+sharedPreferences);
    }

    public void saveBoolean(boolean i){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(REQ_CODE , i);

        editor.apply();
        Log.i("SP","??????????"+sharedPreferences);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_stopwatch:
                    Intent intent = new Intent(CreateAlarm.this, StopwatchMain.class);
                    startActivity(intent);
                    break;

                case R.id.nav_timer:
                    Intent intent2 = new Intent(CreateAlarm.this, TimerMain.class);
                    startActivity(intent2);
                    break;

            }
            return true;
        }
    };

    public CreateAlarm getInstance(){
        return inst;
    }

    public void updateTextView(String q){
        tv_question.setText(q);

    }

    public void setVisibility(){
        rl.setVisibility(View.VISIBLE);
    }


}
