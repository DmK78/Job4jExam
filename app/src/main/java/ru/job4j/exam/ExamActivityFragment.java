package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.job4j.exam.Data.Exam;
import ru.job4j.exam.Data.Option;
import ru.job4j.exam.Data.Question;

public class ExamActivityFragment extends Fragment implements ConfirmHintDialogFragment.ConfirmHintDialogListener {

    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION = "question";
    private int position = 0;
    private boolean isLastAnswerWasRight = false;
    private RadioGroup radioGroupVariants;
    private Button buttonNext;
    private Button buttonHint;
    private Exam currentExam;
    private Button buttonPrew;
    private TextView textViewQuestion;
    private boolean readOnly = false;
    private ExamsCore examsCore = ExamsCore.getInstance();
    //List<Question> questions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (examsCore.examUptading) {
            //getfromDB
            //String tempName = examsCore.getCurrentExam().getName();
            examsCore.setCurrentExam(examsCore.getExamFromDb(examsCore.getCurrentExamTempId()));
            currentExam=examsCore.getCurrentExam();

        } else {
            examsCore.setCurrentExam(new Exam(examsCore.getCurrentExamTempName(), "", "", "", examsCore.getNewQuestions()));
            currentExam=examsCore.getCurrentExam();
        }
        View view = inflater.inflate(R.layout.activity_main, container, false);
        position = 0;
        radioGroupVariants = view.findViewById(R.id.RadioGroupVariants);
        buttonNext = view.findViewById(R.id.buttonNext);
        buttonHint = view.findViewById(R.id.buttonHint);
        buttonPrew = view.findViewById(R.id.buttonPrew);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int id = radioGroupVariants.getCheckedRadioButtonId();

                int radioButtonID = radioGroupVariants.getCheckedRadioButtonId();
                View radioButton = radioGroupVariants.findViewById(radioButtonID);
                int idx = radioGroupVariants.indexOfChild(radioButton);

                RadioButton r = (RadioButton)radioGroupVariants.getChildAt(idx);
                String s = r.getTag().toString();
                int id=Integer.valueOf(s);

                if (id == -1) {
                    Toast.makeText(getContext(), "Choose the answer", Toast.LENGTH_SHORT).show();
                } else {
                    currentExam.getQuestions().get(position).setAnswer(id - 1);
                    if (position == currentExam.getQuestions().size() - 1) {
                        //examsCore.setCurrentExam(currentExam);
                        Intent intent = new Intent(getContext(), ResultActivity.class);
                        startActivity(intent);
                    } else {
                        position++;
                        fillForm();
                    }
                }
            }
        });
        buttonPrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radioGroupVariants.getCheckedRadioButtonId();
                position--;
                fillForm();
            }
        });
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new ConfirmHintDialogFragment();
                dialog.show(getFragmentManager(), "dialog_tag");
            }
        });
        this.fillForm();
        return view;
    }


    private void fillForm() {
        buttonPrew.setEnabled(position != 0);
        //buttonNext.setEnabled(position <= questions.size() - 1 && readOnly);
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

    @Override
    public void onPositiveHintDialogClick(DialogFragment dialog) {
        // Intent intent = new Intent(getApplicationContext(), HintActivity.class);
        // intent.putExtra(HINT_FOR, position);
        // intent.putExtra(QUESTION, ExamActivityFragment.this.questions.get(position).getText());
        //  startActivity(intent);
    }

    @Override
    public void onNegativeHintDialogClick(DialogFragment dialog) {
        // Toast.makeText(this, "Молодец!!!", Toast.LENGTH_SHORT).show();

    }
}
