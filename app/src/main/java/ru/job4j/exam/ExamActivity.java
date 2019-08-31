package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ExamActivity extends AppCompatActivity implements ConfirmHintDialogFragment.ConfirmHintDialogListener {
    private final FragmentManager manager = getSupportFragmentManager();
    private ExamsCore examsCore = ExamsCore.getInstance();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam);
        Fragment fragment = manager.findFragmentById(R.id.fragmentExam);
        if (fragment == null) {
            fragment = new ExamActivityFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentExam, fragment)
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
}
