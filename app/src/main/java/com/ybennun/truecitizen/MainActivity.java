package com.ybennun.truecitizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ybennun.truecitizen.databinding.ActivityMainBinding;
import com.ybennun.truecitizen.model.Question;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;


    private Question[] questionBank = new Question[]{
            //create/instantiate question objects
            new Question(R.string.question_amendments, false),//correct: 27
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
        binding.trueButton.setOnClickListener(view -> {
            checkAnswer(true);
        });
        binding.falseButton.setOnClickListener(view -> {
            checkAnswer(false);
        });

        binding.nextButton.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;//incrementing

            updateQuestion();
        });

        binding.prevButton.setOnClickListener(view -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;//decrementing
                updateQuestion();
            }
        });

    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answerCorrect = questionBank[currentQuestionIndex].isAnswerTrue();
        int messageId;

        if (answerCorrect == userChoseCorrect) {
            messageId = R.string.correct_answer;
        } else {
            messageId = R.string.wrong_answer;
        }

        Snackbar.make(binding.imageView, messageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }
}