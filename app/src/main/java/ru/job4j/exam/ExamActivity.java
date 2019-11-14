package ru.job4j.exam;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import ru.job4j.exam.Data.Exam;
import ru.job4j.exam.Data.Option;
import ru.job4j.exam.Data.Question;

public class ExamActivity extends AppCompatActivity implements ConfirmHintDialog.ConfirmHintDialogListener {

    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION = "question";
    private int position = 0;
    private RadioGroup radioGroupVariants;
    private Button buttonNext;
    private Button buttonHint;
    private Exam currentExam;
    private Button buttonPrev;
    private TextView textViewQuestion;
    @Inject
    ExamsCore examsCore;
    private int examId;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        App.getExamCore().injectTo(this);
        Intent intent = getIntent();
        if (intent != null) {
            examId = intent.getIntExtra(ExamListFragment.EXAM_ID, -1);
        }
        currentExam = examsCore.getExamFromDb(examId);
        int count = 0;
        for (Question question : currentExam.getQuestions()) {

            if (question.getAnswer() != -1) {
                position = question.getPosition();
                count++;
            }
        }
        if (count == currentExam.getQuestions().size()) {
            position = 0;
        }
        radioGroupVariants = findViewById(R.id.RadioGroupVariants);
        buttonNext = findViewById(R.id.buttonNext);
        buttonHint = findViewById(R.id.buttonHint);
        buttonPrev = findViewById(R.id.buttonPrew);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonNext.setOnClickListener(this::nextButton);
        buttonPrev.setOnClickListener(this::prevButton);
        buttonHint.setOnClickListener(this::hintButton);
        this.fillForm();
    }

    private void fillForm() {
        buttonPrev.setEnabled(position != 0);
        radioGroupVariants.clearCheck();
        Question question = currentExam.getQuestions().get(position);
        textViewQuestion.setText(question.getName());
        for (int index = 0; index != radioGroupVariants.getChildCount(); index++) {
            RadioButton button = (RadioButton) radioGroupVariants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
            if (question.getAnswer() == index) {
                button.setChecked(true);
            }
        }
    }

    private void nextButton(View view) {
        int radioButtonID = radioGroupVariants.getCheckedRadioButtonId();
        if (radioButtonID < 0) {
            Toast.makeText(getApplicationContext(), "Choose the answer", Toast.LENGTH_SHORT).show();
        } else {
            View radioButton = radioGroupVariants.findViewById(radioButtonID);
            int idx = radioGroupVariants.indexOfChild(radioButton);
            RadioButton r = (RadioButton) radioGroupVariants.getChildAt(idx);
            String s = r.getTag().toString();
            int id = Integer.valueOf(s);
            currentExam.getQuestions().get(position).setAnswer(id - 1);
            examsCore.saveQuestionToDb(currentExam.getId(), currentExam.getQuestions().get(position));
            if (position == currentExam.getQuestions().size() - 1) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(ExamListFragment.EXAM_ID, currentExam.getId());
                startActivity(intent);
            } else {
                position++;
                fillForm();
            }
        }
    }

    private void prevButton(View view) {
        position--;
        fillForm();
    }

    private void hintButton(View view) {
        DialogFragment dialog = new ConfirmHintDialog();
        dialog.show(getSupportFragmentManager(), "dialog_tag");
    }

    @Override
    public void onPositiveHintDialogClick(DialogFragment dialog) {
        Intent intent = new Intent(getApplicationContext(), HintActivity.class);
        intent.putExtra(ExamActivity.HINT_FOR, position);
        intent.putExtra(ExamActivity.QUESTION, currentExam.getQuestions().get(position).getName());
        startActivity(intent);
    }

    @Override
    public void onNegativeHintDialogClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), "Молодец!!!", Toast.LENGTH_SHORT).show();
    }
}
