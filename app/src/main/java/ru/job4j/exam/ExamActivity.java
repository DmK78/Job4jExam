package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class ExamActivity extends AppCompatActivity implements
        ConfirmHintDialogFragment.ConfirmHintDialogListener, ExamSetNameFragment.OnSaveExamNameButtonClickListener {
    private final FragmentManager manager = getSupportFragmentManager();
    private ExamsCore examsCore = ExamsCore.getInstance();
    private String examName;
    private int examId;
    private boolean examUpdating;
    private Fragment setExamNameFragment;
    private Fragment startExamFragment;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam);

        examUpdating = getIntent().getBooleanExtra(ExamListFragment.EXAM_UPDATING, false);
        Bundle bundle = new Bundle();
        if (examUpdating) {
            examId = getIntent().getIntExtra(ExamListFragment.EXAM_ID, 0);
            examName = getIntent().getStringExtra(ExamListFragment.EXAM_NAME);
            bundle.putString(ExamListFragment.EXAM_NAME, examName);
            bundle.putInt(ExamListFragment.EXAM_ID, examId);
            bundle.putBoolean(ExamListFragment.EXAM_UPDATING, examUpdating);
        }

        setExamNameFragment = manager.findFragmentById(R.id.fragmentExam);
        if (setExamNameFragment == null) {
            setExamNameFragment = new ExamSetNameFragment();
            setExamNameFragment.setArguments(bundle);
            manager.beginTransaction()
                    .add(R.id.fragmentExam, setExamNameFragment)
                    .commit();
        }
    }

    @Override
    public void onPositiveHintDialogClick(DialogFragment dialog) {
        Intent intent = new Intent(getApplicationContext(), HintActivity.class);
        intent.putExtra(ExamActivityFragment.HINT_FOR, examsCore.getCurrentQuestionPosition());
        intent.putExtra(ExamActivityFragment.QUESTION, examsCore.getCurrentExam().getQuestions().get(examsCore.getCurrentQuestionPosition()).getName());
        startActivity(intent);
    }

    @Override
    public void onNegativeHintDialogClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), "Молодец!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveButtonClicked(String examName, int id, boolean examUpdating) {
        Bundle bundle = new Bundle();
        bundle.putString(ExamListFragment.EXAM_NAME, examName);
        bundle.putInt(ExamListFragment.EXAM_ID, examId);
        bundle.putBoolean(ExamListFragment.EXAM_UPDATING, examUpdating);
        startExamFragment = manager.findFragmentById(R.id.fragmentExam);
        if (startExamFragment == null) {
            startExamFragment = new ExamActivityFragment();
            startExamFragment.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.fragmentExam, startExamFragment)
                    .commit();
        }


    }
}
