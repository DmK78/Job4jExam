package ru.job4j.exam;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.job4j.exam.Data.Exam;

public class MainActivity extends AppCompatActivity implements
        ConfirmDeleteAllItemsDialog.ConfirmDeleteAllItemsDialogListener, ExamListFragment.OnExamListButtonClickListener {
    private final FragmentManager manager = getSupportFragmentManager();
    private ExamsCore examsCore = ExamsCore.getInstance();
    private Fragment fragment = manager.findFragmentById(R.id.list);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam_list);
        examsCore.init(getApplicationContext());
        if (fragment == null) {
        fragment = new ExamListFragment();
        manager.beginTransaction()
                .add(R.id.list, fragment)
                .commit();
        }
    }

    @Override
    public void onPositiveDelItemsDialogClick(DialogFragment dialog) {
        if (examsCore.deleteAllExamsFromDb()) {
            ExamListFragment fragment = (ExamListFragment) getSupportFragmentManager().findFragmentById(R.id.list);
            fragment.clearExams();
            Toast.makeText(getApplicationContext(), "All exams was deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeDelItemsDialogClick(DialogFragment dialog) {
    }

    @Override
    public void onMenuAddExamButtonClicked() {
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        startActivity(intent);

    }

    @Override
    public void onEditExamButtonClicked(Exam exam) {
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra(ExamListFragment.EXAM_NAME, exam.getName());
        intent.putExtra(ExamListFragment.EXAM_ID, exam.getId());
        intent.putExtra(ExamListFragment.EXAM_UPDATING, true);
        startActivity(intent);
    }


}