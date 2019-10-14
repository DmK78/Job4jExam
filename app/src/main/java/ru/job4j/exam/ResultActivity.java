package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import ru.job4j.exam.Data.Exam;
import ru.job4j.exam.Data.Question;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewRightAnswers;
    private TextView textViewAnswers;
    public static final String CURRENT_DATE = "currentDate";
    public static final String PERCENT_OF_RIGHT_ANSWERS = "percentOfRightAnswers";
    public static final String HAS_SAVED = "hasSaved";
    @Inject
    ExamsCore examsCore;
    private Exam currentExam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        App.getExamCore().injectTo(this);
        textViewRightAnswers = findViewById(R.id.textViewRightAnswers);
        textViewAnswers = findViewById(R.id.textViewTotal);
        Intent intent = getIntent();
        if (intent != null) {
            currentExam = examsCore.getExamFromDb(intent.getIntExtra(ExamListFragment.EXAM_ID, -1));
        }
        countResult(currentExam);
        textViewAnswers.setText("Quantity of questions: " + currentExam.getQuestions().size());
        textViewRightAnswers.setText("Right answers: " + currentExam.getResult());
    }

    public void saveExam(View view) {
        examsCore.updateExamToDb(currentExam);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void countResult(Exam currentExam) {
        int rightAnswers = 0;
        for (Question question : currentExam.getQuestions()) {
            if (question.getRigthAnswer() == question.getAnswer() + 1) {
                rightAnswers++;
            }
        }
        float result = (float) rightAnswers / (float) currentExam.getQuestions().size() * 100;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
        String currentDateandTime = sdf.format(new Date());
        this.currentExam.setResult(String.valueOf((int) result + " %"));
        this.currentExam.setDate(currentDateandTime);
    }
}
