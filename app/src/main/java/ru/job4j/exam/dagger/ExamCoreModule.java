package ru.job4j.exam.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.job4j.exam.utils.ExamsCore;


@Module
public class ExamCoreModule {
    @Singleton
    @Provides
    public ExamsCore providesExamCore() {
        return new ExamsCore();
    }
}
