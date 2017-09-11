package com.example.mousa.audiostudy;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    AudioManager audioManager;
    SeekBar seekPlayer, volume;
    int maxVolume, curVolume, maxPlayer;

    public void playAudio(View view) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.laugh);
            maxPlayer = player.getDuration();
            seekPlayer.setMax(maxPlayer);
            player.start();
        } else {
            player.start();
        }

    }

    public void pauseAudio(View view) {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
            }
        }

    }

    public void stopAudio(View view) {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
            seekPlayer.setProgress(0);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volume = (SeekBar) findViewById(R.id.seekBar);

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // to set the maxium audio as the same as the phone
        curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volume.setMax(maxVolume);
        volume.setProgress(curVolume);


        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekPlayer = (SeekBar) findViewById(R.id.seekBar2);


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (player != null)
                    seekPlayer.setProgress(player.getCurrentPosition());
            }
        }, 0, 1000);

        seekPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null)
                    player.seekTo(progress); // update
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (player != null)
                    player.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null)
                    player.start();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
