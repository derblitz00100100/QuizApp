package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int currentQuestion = 0;
    private List<Question> questions;
    private int score;
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream JsonFileInputStream = getResources().openRawResource(R.raw.questions);
        String testText = readTextFile(JsonFileInputStream);

        questionText.setText(questions.get(currentQuestion).getQuestion());

        wireWidgets();
        setListeners();
    }

    private void setListeners() {
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAnswer(true)) {
                    score++;
                    nextQuestion();
                }
                else {
                    nextQuestion();
                }

            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        return questions.get(currentQuestion).getAnswer() == selectedAnswer;
    }

    public boolean hasMoreQuestions() {
        if (questions.size() - 1 > currentQuestion)
            return true;
        else
            return false;
    }

    public Question nextQuestion() {
        currentQuestion++;
        return questions.get(currentQuestion);
    }
}

