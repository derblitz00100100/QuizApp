package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Quiz quiz;
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;

    public static final String EXTRA_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();

        InputStream JsonFileInputStream = getResources().openRawResource(R.raw.questions);
        String jsonString = readTextFile(JsonFileInputStream);
        Gson gson = new Gson();
        Question[] questions =  gson.fromJson(jsonString, Question[].class);
        quiz = new Quiz(Arrays.asList(questions));
        Log.d(TAG, "onCreate: " + quiz.toString());
        questionText.setText(quiz.getQuestions().get(quiz.getCurrentQuestion()).getQuestion());

    }

    private void setListeners() {
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quiz.checkAnswer(true)) {
                    quiz.setScore(quiz.getScore() + 1);
                }
                if (quiz.hasMoreQuestions()) {
                        questionText.setText(quiz.nextQuestion());
                }
                else if (!quiz.hasMoreQuestions()){
                    Intent targetIntent = new Intent(MainActivity.this, ScoreActivity.class);
                    targetIntent.putExtra(EXTRA_SCORE, quiz.getScore());
                    startActivity(targetIntent);
                    finish();
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quiz.checkAnswer(false)) {
                    quiz.setScore(quiz.getScore() + 1);
                }
                if (quiz.hasMoreQuestions()){
                    questionText.setText(quiz.nextQuestion());
                }
                else if (!quiz.hasMoreQuestions()){
                    Intent targetIntent = new Intent(MainActivity.this, ScoreActivity.class);
                    targetIntent.putExtra(EXTRA_SCORE, quiz.getScore());
                    startActivity(targetIntent);
                    finish();
                }
            }
        });
    }

    private void wireWidgets() {
        trueButton = findViewById(R.id.button_main_restart);
        falseButton = findViewById(R.id.button_main_false);
        questionText = findViewById(R.id.textView_main_score);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
    // reading textfile from res folder, https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name

}

