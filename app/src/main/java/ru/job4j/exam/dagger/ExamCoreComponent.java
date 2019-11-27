package ru.job4j.exam.dagger;


import javax.inject.Singleton;

import dagger.Component;
import ru.job4j.exam.ExamsListActivity;
import ru.job4j.exam.QuestionFragment;
import ru.job4j.exam.ExamListFragment;
import ru.job4j.exam.ResultActivity;

@Singleton
@Component(modules = ExamCoreModule.class)
public interface ExamCoreComponent {
    void injectTo(ExamListFragment examListFragment);
    void injectTo(ExamsListActivity examsListActivity);
    void injectTo(QuestionFragment questionFragment);
    void injectTo(ResultActivity resultActivity);
}
