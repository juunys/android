package com.juny.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    TextView sumTextView;
    TextView scoreTextView;
    TextView timeTextView;
    TextView resultTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;

    ConstraintLayout gameLayout;
    GridLayout gridLayout;

    int score = 0;
    int locationOfCorrectAnswer;
    int question;


    public void start(View view) {

        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timeTextView));

    }

    public void chooseAnswer(View view) {

        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {

            resultTextView.setText("CORRECT!");
            score++;

        } else {

            resultTextView.setText("WRONG :(");

        }

        question++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(question));
        newQuestion();

    }

    public void playAgain(View view) {
        score = 0;
        question = 0;
        timeTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(question));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        resultTextView.setText("");

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                timeTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);
                timeTextView.setText("0s");
                gridLayout.setVisibility(View.INVISIBLE);

            }
        }.start();
    }

    public void newQuestion() {

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = random.nextInt(4);
        answers.clear();

        for (int i = 0; i < 4; i++) {

            if (locationOfCorrectAnswer == i) {

                answers.add(a+b);

            } else {

                int wrongAnswer = random.nextInt(41);
                while (wrongAnswer == a+b) {

                    wrongAnswer = random.nextInt(41);

                }

                answers.add(wrongAnswer);

            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setVisibility(View.INVISIBLE);
        gridLayout = findViewById(R.id.gridLayout);

        sumTextView = findViewById(R.id.sumTextView);
        timeTextView = findViewById(R.id.timeTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        goButton = findViewById(R.id.goButton);
        goButton.setVisibility(View.VISIBLE);

    }
}
