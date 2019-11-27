package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.models.Question;
import ru.job4j.exam.utils.ExamsCore;

public class ResultFragment extends Fragment {
    private TextView textViewRightAnswers;
    private TextView textViewAnswers;
    private Button buttonSaveExam;
    @Inject
    ExamsCore examsCore;
    private Exam currentExam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_result, container, false);
        App.getExamCore().injectTo(this);
        textViewRightAnswers = view.findViewById(R.id.textViewRightAnswers);
        textViewAnswers = view.findViewById(R.id.textViewTotal);
        buttonSaveExam = view.findViewById(R.id.buttonSaveExam);
        buttonSaveExam.setOnClickListener(this::saveExam);
        currentExam = examsCore.getExamFromDb(getArguments().getInt(ExamListFragment.EXAM_ID, -1));

        countResult(currentExam);
        textViewAnswers.setText("Quantity of questions: " + currentExam.getQuestions().size());
        textViewRightAnswers.setText("Right answers: " + currentExam.getResult());


        return view;

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

    public void saveExam(View view) {
        examsCore.updateExamToDb(currentExam);
        Intent intent = new Intent(getContext(), ExamsListActivity.class);
        startActivity(intent);
    }

    public static ResultFragment of(int examId) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ExamListFragment.EXAM_ID, examId);
        resultFragment.setArguments(bundle);
        return resultFragment;
    }
}
