package ru.job4j.exam;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.models.Question;
import ru.job4j.exam.utils.ExamsCore;

public class ResultActivity extends BaseActivity {


    @Override
    public Fragment loadFrg() {
        return ResultFragment.of(getIntent().getIntExtra(ExamListFragment.EXAM_ID,0));
    }


}
