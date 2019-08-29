package ru.job4j.exam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final FragmentManager manager = getSupportFragmentManager();
    private ExamsCore examsCore = ExamsCore.getInstance();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_exam_list);
        examsCore.init(getApplicationContext());
        Fragment fragment = manager.findFragmentById(R.id.list);
        //if (fragment == null) {
            fragment = new ExamListFragment();
            manager.beginTransaction()
                    .add(R.id.list, fragment)
                    .commit();
        //}
    }
}