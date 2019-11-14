package ru.job4j.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.exam_host);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.exam_container) == null) {
            fm.beginTransaction()
                    .add(R.id.exam_container, loadFrg())
                    .commit();
        }
    }


    public abstract Fragment loadFrg();



}
