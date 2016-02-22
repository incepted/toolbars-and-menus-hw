package com.example.envy.toolbars_and_menus_hw;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {


    ImageView mButtonThumbsUp, mButtonThumbsDown, mButtonPlay, mButtonSkip;
    SeekBar mSeekBar;
    MediaPlayer mMediaPlayer;
    TextView timeElapsedTextView, timeRemainingTextView;
    double maxTime, elapsedTime;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarTitle);
        setSupportActionBar(toolbar);
        //Removes default label
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mMediaPlayer = MediaPlayer.create(this,R.raw.bensound_funnysong);
        mButtonThumbsUp = (ImageView)findViewById(R.id.button_thumpsup);
        mButtonThumbsDown = (ImageView)findViewById(R.id.button_thumpsdown);
        mButtonPlay = (ImageView)findViewById(R.id.button_play);
        mButtonSkip = (ImageView)findViewById(R.id.button_skip);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        timeElapsedTextView = (TextView) findViewById(R.id.time_elapsed);
        timeRemainingTextView = (TextView) findViewById(R.id.time_remaining);

        maxTime = mMediaPlayer.getDuration();
        mSeekBar.setMax((int) maxTime);

        int remainingMin = (int)(TimeUnit.MILLISECONDS.toMinutes((long)maxTime));
        int remainingSec = (int)(TimeUnit.MILLISECONDS.toSeconds((long)maxTime) - TimeUnit.MINUTES.toSeconds((long)TimeUnit.MILLISECONDS.toMinutes((long)maxTime)));
        if(remainingSec<10){
            timeRemainingTextView.setText(remainingMin + ":0" + remainingSec);
        }else {
            timeRemainingTextView.setText(remainingMin + ":" + remainingSec);
        }

        mButtonThumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Liked!", Toast.LENGTH_LONG).show();
            }
        });

        mButtonThumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Disliked!", Toast.LENGTH_LONG).show();
            }
        });


        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mButtonPlay.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mMediaPlayer.start();
                    mButtonPlay.setImageResource(android.R.drawable.ic_media_pause);
                    elapsedTime = mMediaPlayer.getCurrentPosition();
                    mSeekBar.setProgress((int)elapsedTime);
                    handler.postDelayed(updateTimeRunnable,100);
                }
            }
        });
    }

    Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = mMediaPlayer.getCurrentPosition();
            mSeekBar.setProgress((int)elapsedTime);

            double remainingTime = maxTime - elapsedTime;
            int remainingMin = (int)(TimeUnit.MILLISECONDS.toMinutes((long)remainingTime));
            int remainingSec = (int)(TimeUnit.MILLISECONDS.toSeconds((long)remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)remainingTime)));
            if(remainingSec<10){
                timeRemainingTextView.setText(remainingMin + ":0" + remainingSec);
            }else {
                timeRemainingTextView.setText(remainingMin + ":" + remainingSec);
            }

            int elapsedMin = (int)(TimeUnit.MILLISECONDS.toMinutes((long)elapsedTime));
            int elapsedSec = (int)(TimeUnit.MILLISECONDS.toSeconds((long)elapsedTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)elapsedTime)));
            if(elapsedSec<10){
                timeElapsedTextView.setText(elapsedMin + ":0" + elapsedSec);
            }else {
                timeElapsedTextView.setText(elapsedMin + ":" + elapsedSec);
            }

            handler.postDelayed(this,100);
        }
    };

}
