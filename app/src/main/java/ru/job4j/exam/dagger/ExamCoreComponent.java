package ru.job4j.exam.dagger;


import javax.inject.Singleton;

import dagger.Component;
import ru.job4j.exam.QuestionFragment;
import ru.job4j.exam.ExamListFragment;
import ru.job4j.exam.MainActivity;
import ru.job4j.exam.ResultActivity;

@Singleton
@Component(modules = ExamCoreModule.class)
public interface ExamCoreComponent {
    void injectTo(ExamListFragment examListFragment);
    void injectTo(MainActivity mainActivity);
    void injectTo(QuestionFragment examActivity);
    void injectTo(ResultActivity resultActivity);
}
