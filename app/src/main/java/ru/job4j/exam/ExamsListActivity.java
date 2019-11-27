package ru.job4j.exam;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import javax.inject.Inject;

import ru.job4j.exam.utils.ExamsCore;

public class ExamsListActivity extends BaseActivity implements
        ConfirmRestartExamDialog.ConfirmRestartExamDialogListener {
    @Inject
    ExamsCore examsCore;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        App.getExamCore().injectTo(this);
        examsCore.init(getApplicationContext());
    }

    @Override
    public Fragment loadFrg() {
        return new ExamListFragment();
    }

    @Override
    public void onPositiveRestartExamDialogClick(DialogFragment dialog, int id) {
        examsCore.clearExam(examsCore.getExamFromDb(id));
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra(ExamListFragment.EXAM_ID, id);
        startActivity(intent);
    }

    @Override
    public void onNegativeRestartExamDialogClick(DialogFragment dialog) {
    }

}