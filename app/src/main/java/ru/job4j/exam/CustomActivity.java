package ru.job4j.exam;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;

public class CustomActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    private EditText editTextSelectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        editTextSelectDate = (EditText) findViewById(R.id.editText1);
        editTextSelectDate.setInputType(InputType.TYPE_NULL);
        editTextSelectDate.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(CustomActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                            editTextSelectDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        }
                    }, year, month, day);
            picker.show();
        });
    }
}