package ru.job4j.exam;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private static final String TAG = "ExamActivity";
    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION = "question";
    public static final String RIGHT_ANSWERS = "right_answers";
    public static final String ANSWERS_SUM = "answers_sum";
    private int rotateCounter = 0;
    private int position = 0;
    private int rightAnswers = 0;
    private boolean isLastAnswerWasRight = false;
    RadioGroup radioGroupVariants;
    Button buttonNext;
    Button buttonHint;
    Button buttonPrew;
    TextView textViewQuestion;
    private final List<Question> questions = Arrays.asList(
            new Question(
                    1, "How many primitive variables does Java have?",
                    Arrays.asList(
                            new Option(1, "1.1"), new Option(2, "1.2"),
                            new Option(3, "1.3"), new Option(4, "1.4")
                    ), 4
            ),
            new Question(
                    2, "What is Java Virtual Machine?",
                    Arrays.asList(
                            new Option(1, "2.1"), new Option(2, "2.2"),
                            new Option(3, "2.3"), new Option(4, "2.4")
                    ), 4
            ),
            new Question(
                    3, "What is happen if we try unboxing null?",
                    Arrays.asList(
                            new Option(1, "3.1"), new Option(2, "3.2"),
                            new Option(3, "3.3"), new Option(4, "3.4")
                    ), 4
            )
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            rotateCounter = savedInstanceState.getInt("rotateCount");
        }
        Log.d(TAG, "onCreate");
        Log.d(TAG, "rotate counter is " + rotateCounter);
        radioGroupVariants = findViewById(R.id.RadioGroupVariants);
        buttonNext = findViewById(R.id.buttonNext);
        buttonHint = findViewById(R.id.buttonHint);
        buttonPrew = findViewById(R.id.buttonPrew);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        this.fillForm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("rotateCount", ++rotateCounter);
    }

    private void fillForm() {
        buttonPrew.setEnabled(position != 0);
        //buttonNext.setEnabled(position != questions.size() - 1);
        radioGroupVariants.clearCheck();
        Question question = questions.get(position);
        textViewQuestion.setText(question.getText());
        for (int index = 0; index != radioGroupVariants.getChildCount(); index++) {
            RadioButton button = (RadioButton) radioGroupVariants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
            if (index == question.getUserChoise()) {
                button.setChecked(true);
            }
        }
    }

    private void showAnswer() {
        int id = radioGroupVariants.getCheckedRadioButtonId();
        Question question = this.questions.get(this.position);
        Toast.makeText(this, String.format("Your answer is %d, correct is %d",
                id, question.getAnswer()), Toast.LENGTH_SHORT)
                .show();
    }

    private void saveUserChoise(int id) {
        Question question = questions.get(position);
        question.setUserChoise(id - 1);
    }

    public void onClickNext(View view) {
        int id = radioGroupVariants.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(ExamActivity.this, "Choose the answer", Toast.LENGTH_SHORT).show();
        } else {
            if (id == questions.get(position).getAnswer()) {
                rightAnswers++;
                isLastAnswerWasRight = true;
            }
            saveUserChoise(id);
            showAnswer();
            if (position == questions.size() - 1) {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra(RIGHT_ANSWERS, rightAnswers);
                intent.putExtra(ANSWERS_SUM, questions.size());
                startActivity(intent);
            } else {
                position++;
                fillForm();
            }
        }
    }

    public void onClickPrev(View view) {
        int id = radioGroupVariants.getCheckedRadioButtonId();
        if (id != -1) {
            saveUserChoise(id);
        }
        if (isLastAnswerWasRight) {
            rightAnswers--;
            isLastAnswerWasRight = false;
        }
        position--;
        fillForm();
    }


    public void onClickHint(View view) {
        Intent intent = new Intent(ExamActivity.this, HintActivity.class);
        intent.putExtra(HINT_FOR, position);
        intent.putExtra(QUESTION, ExamActivity.this.questions.get(position).getText());
        ExamActivity.this.startActivity(intent);
    }
}
