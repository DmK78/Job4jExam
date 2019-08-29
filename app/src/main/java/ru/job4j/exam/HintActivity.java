package ru.job4j.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HintActivity extends AppCompatActivity {
    Button buttonBack;
    TextView textViewHint;
    TextView textViewQuestion;

    private final Map<Integer, String> answers = new HashMap<Integer, String>();

    public HintActivity() {
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        buttonBack = findViewById(R.id.back);
        textViewHint = findViewById(R.id.textViewHint);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        Intent intent = getIntent();
        int questionId = intent.getIntExtra(ExamActivityFragment.HINT_FOR, 0);
        String question = intent.getStringExtra(ExamActivityFragment.QUESTION);
        textViewQuestion.setText(question);
        textViewHint.setText(this.answers.get(questionId));
        buttonBack.setOnClickListener(
                view -> onBackPressed()
        );
    }

}
