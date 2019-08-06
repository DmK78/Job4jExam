package ru.job4j.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChooseDateTime extends AppCompatActivity implements ChooseDataDialogFragment.ConfirmDateDialogListener,ChooseTimeDialogFragment.ConfirmTimeDialogListener {
    Button buttonChooseDateTime;
    TextView textViewDateTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_time);
        buttonChooseDateTime=findViewById(R.id.buttonChooseDateTime);
        textViewDateTime=findViewById(R.id.textViewDateTime);
    }

    @Override
    public void onPositiveDialogClick(DialogFragment dialog) {

    }

    @Override
    public void onNegativeDialogClick(DialogFragment dialog) {
    }

    public void chooseDateTime(View view) {
        DialogFragment newFragment1 = new ChooseTimeDialogFragment();
        newFragment1.show(getSupportFragmentManager(), "timePicker");
        DialogFragment newFragment = new ChooseDataDialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }


}
