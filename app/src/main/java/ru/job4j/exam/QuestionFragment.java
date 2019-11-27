package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import javax.inject.Inject;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.models.Option;
import ru.job4j.exam.models.Question;
import ru.job4j.exam.utils.ExamsCore;

public class QuestionFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        App.getExamCore().injectTo(this);
        Intent intent = getActivity().getIntent();
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
        radioGroupVariants = view.findViewById(R.id.RadioGroupVariants);
        buttonNext = view.findViewById(R.id.buttonNext);
        buttonHint = view.findViewById(R.id.buttonHint);
        buttonPrev = view.findViewById(R.id.buttonPrew);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        buttonNext.setOnClickListener(this::nextButton);
        buttonPrev.setOnClickListener(this::prevButton);
        buttonHint.setOnClickListener(this::hintButton);
        this.fillForm();
        return view;
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
            Toast.makeText(getContext(), R.string.choose_the_answer, Toast.LENGTH_SHORT).show();
        } else {
            View radioButton = radioGroupVariants.findViewById(radioButtonID);
            int idx = radioGroupVariants.indexOfChild(radioButton);
            RadioButton r = (RadioButton) radioGroupVariants.getChildAt(idx);
            String s = r.getTag().toString();
            int id = Integer.valueOf(s);
            currentExam.getQuestions().get(position).setAnswer(id - 1);
            examsCore.saveQuestionToDb(currentExam.getId(), currentExam.getQuestions().get(position));
            if (position == currentExam.getQuestions().size() - 1) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
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
        DialogFragment dialog = ConfirmHintDialog.of(position, currentExam.getQuestions().get(position).getName());
        dialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "dialog_tag");
    }
    public static QuestionFragment of(int examId) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ExamListFragment.EXAM_ID, examId);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }





}
