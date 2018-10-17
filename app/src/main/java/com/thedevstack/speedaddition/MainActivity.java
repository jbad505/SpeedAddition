package com.thedevstack.speedaddition;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Initialize globals
    private Button go;
    private Button playAgain;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private TextView timerTextView;
    private TextView pointsTextView;
    private TextView sumTextView;
    private TextView resultTextView;

    // Answers ArrayList
    ArrayList<Integer> answers = new ArrayList<>();

    // Get correct answer location
    int correctAnswerLocation = 0;

    // Keep score for correct answers
    int score = 0;

    // Keep running total of questions asked
    int numberOfQuestions = 0;

    // START METHOD
    public void start (View view) {

        // Set Button to invisible onClick
        go.setVisibility(View.INVISIBLE);

        // Start timer
        playAgain(findViewById(R.id.buttPlayAgain));

        // Set Buttons to visible onClick
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);

        // Set TextViews to visible onClick
        timerTextView.setVisibility(View.VISIBLE);
        pointsTextView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);

    }

    public void playAgain(View view) {

        // Reset all variables and buttons for new game
        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        playAgain.setVisibility(View.INVISIBLE);

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);

        // COUNT DOWN TIMER
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                // Disable chooseAnswer Buttons to prevent over clicking after timer expires
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

                // Display score when timer finishes
                resultTextView.setText("Score: " + Integer.toString(score));

                // Set Button to visible
                playAgain.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    // QUESTION GENERATOR METHOD
    public void generateQuestion() {

        //RANDOM NUMBER GENERATOR
        Random rand =  new Random();

        // Generate numbers between 1 - 40
        int x = rand.nextInt(21);
        int y = rand.nextInt(21);

        // Update sumTextView
        sumTextView.setText(Integer.toString(x) + " + " + Integer.toString(y));

        // Correct answer button location
        correctAnswerLocation = rand.nextInt(4);

        // Clear ArrayList for new values
        answers.clear();

        int incorrectAnswer;

        // Loop to set 1 correct answer and 3 incorrect answers on Buttons
        for (int i = 0; i < 4; i++) {

            // Set correct answer to Button then add x and y - correct result
            if ( i == correctAnswerLocation) {

                answers.add(x + y);

            } else {

                // Set incorrect answer to Button - incorrect result
                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == x + y) {

                    // While incorrectAnswer is equal to answer generate a new random number
                    incorrectAnswer = rand.nextInt(41);

                }

                // Add to answers array
                answers.add(incorrectAnswer);
            }
        }

        // Set Button values
        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }

    // Button tapped to choose answer
    public void chooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(correctAnswerLocation))) {

            score++;
            resultTextView.setText("Correct!");

        } else {

            resultTextView.setText("Wrong!");

        }

        // Number of questions generated
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();

    }

    // ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On launch get ID for go button
        go = findViewById(R.id.buttGo);
        playAgain = findViewById(R.id.buttPlayAgain);

        // On launch get ID for answer buttons
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        // On launch get ID for TextViews
        timerTextView = findViewById(R.id.timerTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);

        // On launch set Buttons to invisible
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

        // On launch set TextViews to invisible
        timerTextView.setVisibility(View.INVISIBLE);
        pointsTextView.setVisibility(View.INVISIBLE);
        sumTextView.setVisibility(View.INVISIBLE);

        // On launch generate initial question
        generateQuestion();
    }
}
