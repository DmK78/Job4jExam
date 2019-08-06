package ru.job4j.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewRightAnswers;
    private TextView textViewAnswers;
    public static final String CURRENT_DATE = "currentDate";
    public static final String PERCENT_OF_RIGHT_ANSWERS = "percentOfRightAnswers";
    public static final String HAS_SAVED = "hasSaved";
    private int answersSum;
    private int rightAnswers;
    private float percent;
    private ArrayList<Integer> userChoices;
    private boolean hasSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textViewRightAnswers = findViewById(R.id.textViewRightAnswers);
        textViewAnswers = findViewById(R.id.textViewTotal);
        Intent intent = getIntent();
        answersSum = intent.getIntExtra(ExamActivity.ANSWERS_SUM, 0);
        rightAnswers = intent.getIntExtra(ExamActivity.RIGHT_ANSWERS, 0);
        userChoices = intent.getIntegerArrayListExtra(ExamActivity.USER_CHOICES);
        percent = (float) rightAnswers / (float) answersSum * 100;
        textViewAnswers.setText("Quantity of questions: " + answersSum);
        textViewRightAnswers.setText("Right answers: " + (int)percent+" %");
    }

    public void saveExam(View view) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String currentDateandTime = sdf.format(new Date());
        ExamActivity.clearUserChoices();
        Intent intent = new Intent(this, ExamsActivity.class);
        intent.putExtra(CURRENT_DATE, currentDateandTime);
        intent.putExtra(PERCENT_OF_RIGHT_ANSWERS, percent);
        intent.putIntegerArrayListExtra(ExamActivity.USER_CHOICES, userChoices);
        hasSaved = true;
        intent.putExtra(HAS_SAVED, hasSaved);

        //intent.putExtra(PERCENT_OF_RIGHT_ANSWERS, rightAnswers / (answersSum / 100));

        startActivity(intent);

    }
}
