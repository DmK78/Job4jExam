package ru.job4j.exam.dagger;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Component;
import ru.job4j.exam.ExamActivity;
import ru.job4j.exam.ExamListFragment;
import ru.job4j.exam.MainActivity;
import ru.job4j.exam.ResultActivity;

@Singleton
@Component(modules = ExamCoreModule.class)
public interface ExamCoreComponent {
    void injectTo(ExamListFragment examListFragment);
    void injectTo(MainActivity mainActivity);
    void injectTo(ExamActivity examActivity);
    void injectTo(ResultActivity resultActivity);
}
