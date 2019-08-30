package ru.job4j.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ExamActivity extends AppCompatActivity {
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


}
