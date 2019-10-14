package ru.job4j.exam;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements
        ConfirmRestartExamDialog.ConfirmRestartExamDialogListener {
    private final FragmentManager manager = getSupportFragmentManager();
    @Inject
    ExamsCore examsCore;
    private Fragment fragment = manager.findFragmentById(R.id.list);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam_list);
        App.getExamCore().injectTo(this);
        examsCore.init(getApplicationContext());
        if (fragment == null) {
            fragment = new ExamListFragment();
            manager.beginTransaction()
                    .add(R.id.list, fragment)
                    .commit();
        }
    }


    @Override
    public void onPositiveRestartExamDialogClick(DialogFragment dialog, int id) {
        examsCore.clearExam(examsCore.getExamFromDb(id));
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra(ExamListFragment.EXAM_ID, id);
        startActivity(intent);
    }

    @Override
    public void onNegativeRestartExamDialogClick(DialogFragment dialog) {
    }

}