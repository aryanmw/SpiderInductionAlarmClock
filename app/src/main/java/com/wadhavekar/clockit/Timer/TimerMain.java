package com.wadhavekar.clockit.Timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.clockit.Alarm.CreateAlarm;
import com.wadhavekar.clockit.R;
import com.wadhavekar.clockit.Stopwatch.StopwatchMain;

import java.util.Locale;

public class TimerMain extends AppCompatActivity {

    NumberPicker npMin,npSec;
    TextView tv_min,tv_sec;
    Button start,stop,reset;
    boolean isRunning;
    private CountDownTimer mCountdowntimer;
    private long mTimeLeftInMillis;
    boolean setVal;
    RelativeLayout tv,np;
    ProgressBar progressBar;
    long myTotal;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timer_main);
        npMin = findViewById(R.id.np_timer_mins);
        npSec = findViewById(R.id.np_timer_secs);
        tv_min = findViewById(R.id.tv_mins);
        //tv_sec = findViewById(R.id.tv_secs);
        start = findViewById(R.id.timer_button_start);
        stop = findViewById(R.id.timer_button_stop);
        reset = findViewById(R.id.button_timer_reset);
        tv = findViewById(R.id.rel_tv);
        np = findViewById(R.id.rel_np);
        progressBar = findViewById(R.id.progressBar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        npSec.setValue(10);
        npMin.setMinValue(0);
        npMin.setMaxValue(59);
        npSec.setMinValue(0);
        npSec.setMaxValue(59);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (npMin.getValue()==0 && npSec.getValue()==0){
                    Toast.makeText(TimerMain.this, "Please add some time", Toast.LENGTH_SHORT).show();
                }
                else{

                    if (isRunning){
                        pauseTimer();
                        i--;
                        stop.setVisibility(View.VISIBLE);
                    }
                    else{
                        if (!setVal) {
                            long  minInMilli = npMin.getValue()*60*1000;
                            long secInMilli = npSec.getValue()*1000;
                            long total = minInMilli+secInMilli;
                            mTimeLeftInMillis = total;
                            stop.setVisibility(View.INVISIBLE);
                            myTotal = total;
                            setVal = true;
                            stop.setVisibility(View.INVISIBLE);
                        }
                        tv_min.setVisibility(View.VISIBLE);
                        np.setVisibility(View.INVISIBLE);

                        updateCountdownText();

                        //progressBar.setProgress(0);
                        startTimer();
                    }

                }


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                progressBar.setProgress(0);
                i=0;
            }
        });
    }

    private void startTimer(){
        mCountdowntimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                i++;
                updateCountdownText();
                long prog =  i*100000/myTotal;
                int p = (int) prog;

                progressBar.setProgress(p);

            }

            @Override
            public void onFinish() {
                isRunning = false;
                setVal = false;
                reset.setVisibility(View.INVISIBLE);
                np.setVisibility(View.VISIBLE);
                tv_min.setVisibility(View.INVISIBLE);
                start.setText("Start");
                i=0;
                progressBar.setProgress(0);

                Vibrator vibrator =  (Vibrator)getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));

            }
        }.start();
        isRunning = true;
        start.setText("Pause");
        reset.setVisibility(View.INVISIBLE);

    }
    private void updateCountdownText(){
        int minutes = (int) (mTimeLeftInMillis/1000/60);
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        //long tot = mTimeLeftInMillis/myTotal;

        //progressBar.setProgress();

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tv_min.setText(timeLeftFormatted);

    }
    private void pauseTimer(){
        mCountdowntimer.cancel();
        isRunning = false;
        start.setText("Resume");
        reset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        setVal = false;
        reset.setVisibility(View.INVISIBLE);
        mTimeLeftInMillis = 0;
        updateCountdownText();
        np.setVisibility(View.VISIBLE);
        tv.setVisibility(View.INVISIBLE);
        start.setText("Start");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_alarm:
                    Intent intent = new Intent(TimerMain.this, CreateAlarm.class);
                    startActivity(intent);
                    break;

                case R.id.nav_stopwatch:
                    Intent intent2 = new Intent(TimerMain.this, StopwatchMain.class);
                    startActivity(intent2);
                    break;

            }
            return true;
        }
    };
}
