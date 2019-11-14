package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HintFragment extends Fragment {
    Button buttonBack;
    TextView textViewHint;
    TextView textViewQuestion;

    private final Map<Integer, String> answers = new HashMap<Integer, String>();

    public HintFragment() {
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hint, container, false);
        buttonBack = view.findViewById(R.id.back);
        textViewHint = view.findViewById(R.id.textViewHint);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        Intent intent = getActivity().getIntent();
        int questionId = intent.getIntExtra(QuestionFragment.HINT_FOR, 0);
        String question = intent.getStringExtra(QuestionFragment.QUESTION);
        textViewQuestion.setText(question);
        textViewHint.setText(this.answers.get(questionId));
        buttonBack.setOnClickListener(
                event -> getActivity().onBackPressed()
        );
        return view;
    }



}
