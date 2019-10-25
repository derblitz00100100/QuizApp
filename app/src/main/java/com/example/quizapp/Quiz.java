package com.example.quizapp;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int score;
    private int currentQuestion;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        score = 0;
        currentQuestion = 0;
    }

    public boolean checkAnswer(boolean selectedAnswer) {
        return questions.get(currentQuestion).getAnswer() == selectedAnswer;
    }

    public boolean hasMoreQuestions() {
        if (questions.size() - 1 > currentQuestion)
            return true;
        else
            return false;
    }

    public String nextQuestion() {
        currentQuestion++;
        return questions.get(currentQuestion).getQuestion();
    }


    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> quiz) {
        this.questions = questions;
    }
}
