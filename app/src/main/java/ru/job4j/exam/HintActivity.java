package ru.job4j.exam;

import android.support.v4.app.Fragment;

public class HintActivity extends BaseActivity {
    @Override
    public Fragment loadFrg()
    {
        return HintFragment.of(
                getIntent().getIntExtra(QuestionFragment.HINT_FOR, 0),
                getIntent().getStringExtra(QuestionFragment.QUESTION)

        );
    }
}
