package ru.job4j.exam;

import android.support.v4.app.Fragment;

public class HintActivity extends BaseActivity {
    @Override
    public Fragment loadFrg() {
        return new HintFragment();
    }
}
