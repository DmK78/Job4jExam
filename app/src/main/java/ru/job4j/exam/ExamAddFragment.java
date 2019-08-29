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
import android.widget.EditText;

public class ExamAddFragment extends Fragment {
public static final String EXAM_NAME = "examName";
    Fragment examFragment;
    ExamsCore examsCore = ExamsCore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_add, container, false);

        final EditText edit = view.findViewById(R.id.name);
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        examsCore.setCurrentExamTempName(edit.getText().toString());
                                        Intent intent = new Intent(getContext(), ExamActivity.class);
                                        startActivity(intent);
                                    }
                                });
        return view;
    }
}