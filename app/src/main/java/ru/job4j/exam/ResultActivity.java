package ru.job4j.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView textViewRightAnswers;
    TextView textViewAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textViewRightAnswers = findViewById(R.id.textViewRightAnswers);
        textViewAnswers = findViewById(R.id.textViewTotal);

        Intent intent = getIntent();

        textViewAnswers.setText("Quantity of questions: " + intent.getIntExtra(ExamActivity.ANSWERS_SUM, 0));
        textViewRightAnswers.setText("Right answers: " + intent.getIntExtra(ExamActivity.RIGHT_ANSWERS, 0));
    }
}
