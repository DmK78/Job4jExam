package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.job4j.exam.Data.Exam;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewRightAnswers;
    private TextView textViewAnswers;
    public static final String CURRENT_DATE = "currentDate";
    public static final String PERCENT_OF_RIGHT_ANSWERS = "percentOfRightAnswers";
    public static final String HAS_SAVED = "hasSaved";
    private int answersSum;
    private ExamsCore examsCore = ExamsCore.getInstance();
    private int rightAnswers;
    private float percent;
    private boolean hasSaved = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textViewRightAnswers = findViewById(R.id.textViewRightAnswers);
        textViewAnswers = findViewById(R.id.textViewTotal);
        examsCore.countResult();
        Exam exam=examsCore.getCurrentExam();
        textViewAnswers.setText("Quantity of questions: " + exam.getQuestions().size());
        textViewRightAnswers.setText("Right answers: " + exam.getResult() + " %");
    }





    public void saveExam(View view) {

        if(examsCore.examUptading){
examsCore.getCurrentExam().setName(examsCore.getCurrentExamTempName());
            examsCore.updateExamToDb();

        }else {

            examsCore.saveExamToDb();
        }



        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }
}
