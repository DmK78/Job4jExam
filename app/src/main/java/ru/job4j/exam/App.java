package ru.job4j.exam;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ru.job4j.exam.dagger.DaggerExamCoreComponent;
import ru.job4j.exam.dagger.ExamCoreComponent;

public class App extends Application {
    private static ExamCoreComponent examCore;

    @Override
    public void onCreate() {
        super.onCreate();
        examCore = DaggerExamCoreComponent.create();
    }





    public static ExamCoreComponent getExamCore() {
        return examCore;
    }
}
