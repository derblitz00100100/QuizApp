package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private int currentQuestion = 0;
    private List<Question> questionsList;
    private int score;
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;

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
        questionsList = Arrays.asList(questions);
        Log.d(TAG, "onCreate: " + questionsList.toString());
        questionText.setText(questionsList.get(currentQuestion).getQuestion());
    }

    private void setListeners() {
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAnswer(true) && hasMoreQuestions()) {
                    score++;
                    nextQuestion();
                }
                else {
                    if (hasMoreQuestions())
                    nextQuestion();
                }

            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAnswer(false) && hasMoreQuestions()) {
                    score++;
                    nextQuestion();
                }
                else {
                    if (hasMoreQuestions())
                    nextQuestion();
                }
            }
        });
    }

    private void wireWidgets() {
        trueButton = findViewById(R.id.button_main_true);
        falseButton = findViewById(R.id.button_main_false);
        questionText = findViewById(R.id.textView_main_question);
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

    public boolean checkAnswer(boolean selectedAnswer) {
        return questionsList.get(currentQuestion).getAnswer() == selectedAnswer;
    }

    public boolean hasMoreQuestions() {
        if (questionsList.size() - 1 > currentQuestion)
            return true;
        else
            return false;
    }

    public void nextQuestion() {
        currentQuestion++;
        questionText.setText(questionsList.get(currentQuestion).getQuestion());
    }

}

