package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private Button restartButton;
    private TextView scoreText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        wireWidgets();
        setListeners();

        Intent lastIntent = getIntent();
        int score = lastIntent.getIntExtra(MainActivity.EXTRA_SCORE, 0);

        scoreText.setText(scoreText.getText().toString() + score);
    }

    private void wireWidgets() {
        restartButton = findViewById(R.id.button_main_restart);
        scoreText = findViewById(R.id.textView_main_score);
    }

    private void setListeners() {
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(targetIntent);
                finish();
            }
        });
    }
}
