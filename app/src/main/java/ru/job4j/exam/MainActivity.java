package ru.job4j.exam;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteAllItemsDialog.ConfirmDeleteAllItemsDialogListener {
    private final FragmentManager manager = getSupportFragmentManager();
    private ExamsCore examsCore = ExamsCore.getInstance();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam_list);
        ExamBaseHelper databaseHelper = ExamBaseHelper.getInstance(this);
        examsCore.init(getApplicationContext());
        Fragment fragment = manager.findFragmentById(R.id.list);
        //if (fragment == null) {
        fragment = new ExamListFragment();
        manager.beginTransaction()
                .add(R.id.list, fragment)
                .commit();
        //}
    }

    @Override
    public void onPositiveDelItemsDialogClick(DialogFragment dialog) {
        if (examsCore.deleteAllExamsFromDb()) {
            Toast.makeText(getApplicationContext(), "All exams was deleted", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNegativeDelItemsDialogClick(DialogFragment dialog) {

    }
}