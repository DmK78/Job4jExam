package ru.job4j.exam;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ExamSetNameFragment extends Fragment {
    //ExamsCore examsCore = ExamsCore.getInstance();
    private String examName;
    private int examId;
    private boolean examUpdating;
    private OnSaveExamNameButtonClickListener callback;
    private EditText edit;
    private Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_add, container, false);
        edit = view.findViewById(R.id.title);
        save = view.findViewById(R.id.save);
        if (getArguments() != null) {
            examUpdating = getArguments().getBoolean(ExamListFragment.EXAM_UPDATING, false);
        }
        if (examUpdating) {
            examName = getArguments().getString(ExamListFragment.EXAM_NAME);
            examId = getArguments().getInt(ExamListFragment.EXAM_ID, 0);
            edit.setText(examName);
        }
        save.setOnClickListener(
                btn -> {
                    callback.onSaveButtonClicked(edit.getText().toString(), examId, examUpdating);
                }
        );
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnSaveExamNameButtonClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }

    public interface OnSaveExamNameButtonClickListener {
        void onSaveButtonClicked(String name, int id, boolean examUpdating);
    }
}