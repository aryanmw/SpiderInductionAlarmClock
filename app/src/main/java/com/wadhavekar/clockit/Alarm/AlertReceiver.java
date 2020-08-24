package com.wadhavekar.clockit.Alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.wadhavekar.clockit.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        CreateAlarm calarm = new CreateAlarm();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date dt = new java.sql.Date(utilDate.getTime());



        CreateAlarm ca = new CreateAlarm();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);


        ArrayList<Integer> list;

        list = intent.getIntegerArrayListExtra("key");
        int tone = intent.getIntExtra("rawVal",1);

        MyMedia myMedia = new MyMedia();

        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        cal.setTime(dt);

        if (list.contains(cal.get(Calendar.DAY_OF_WEEK))) {
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification("ClockIt","Alarm is ringing");
            notificationHelper.getManager().notify(1,nb.build());
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));


            if (tone == 1){
                myMedia.soundPlayer(context, R.raw.analog);
            }
            else {
                myMedia.soundPlayer(context,R.raw.schoolbell);
            }

        }
    }
}
