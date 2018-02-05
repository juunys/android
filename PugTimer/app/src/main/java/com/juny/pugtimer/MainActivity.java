package com.juny.pugtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    SeekBar timeSeekBar;
    boolean counterIsActive = false;
    boolean pauseIsActive = false;
    CountDownTimer countDownTimer;
    Button goButton;
    MediaPlayer mPlayer;
    String timeLeft;
    int secondLeft;


    public void resetTimer () {

        timeTextView.setText("0:30");
        timeSeekBar.setProgress(30);
        timeSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
        pauseIsActive = false;

    }

    public void pauseTimer() {

        counterIsActive = false;
        pauseIsActive = true;
        timeSeekBar.setEnabled(true);
        timeTextView.setText(timeLeft);
        timeSeekBar.setProgress(secondLeft);
        countDownTimer.cancel();
        goButton.setText("CONTINUE!");

    }

    public void goButton(View view) {

        if (counterIsActive) {

            //resetTimer();
            pauseTimer();

        } else {

            counterIsActive = true;
            timeSeekBar.setEnabled(false);
            goButton.setText("PAUSE!");

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {


                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {

                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dog);
                    mPlayer.start();
                    resetTimer();

                }
            }.start();

        }

    }

    public void updateTimer(int secondsLeft) {

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {

            secondString = "0" + secondString;

        }

        timeLeft = Integer.toString(minutes) + ":" + secondString;
        secondLeft = (minutes*60) + Integer.parseInt(secondString);
        timeTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.textView);
        timeSeekBar = findViewById(R.id.timeSeekBar);
        goButton = findViewById(R.id.goButton);

        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}
