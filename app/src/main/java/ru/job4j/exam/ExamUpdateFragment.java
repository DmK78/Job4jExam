package ru.job4j.exam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.job4j.exam.Data.Exam;

public class ExamUpdateFragment extends Fragment {
ExamsCore examsCore = ExamsCore.getInstance();
    private SQLiteDatabase store;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_update, container, false);
        //this.store = new ExamBaseHelper(this.getContext()).getWritableDatabase();
        //Bundle args = getArguments();
        final EditText edit = view.findViewById(R.id.title);
        edit.setText(examsCore.getCurrentExamTempName());
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(
                btn -> {

                    examsCore.examUptading=true;
                    examsCore.setCurrentExamTempName(edit.getText().toString());

                    //examsCore.getCurrentExam().setName(edit.getText().toString());
                    Intent intent = new Intent(getContext(),ExamActivity.class);
                    startActivity(intent);
                }
        );
        return view;
    }
}