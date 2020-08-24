package com.wadhavekar.clockit.Alarm;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMedia {
    public static MediaPlayer player;


    public void soundPlayer(Context ctx, int raw_id){
        player = MediaPlayer.create(ctx, raw_id);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);

        //player.release();
        player.start();
    }

    public void stopPlayer(){
        player.stop();
    }


}
