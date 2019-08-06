package ru.job4j.exam;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChooseDataDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private ConfirmDateDialogListener callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textViewDateTime = (TextView) getActivity().findViewById(R.id.textViewDateTime);
        // Create a Date variable/object with user chosen date
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);
        Date chosenDate = cal.getTime();
        // Format the date using style and locale
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String formattedDate = df.format(chosenDate);
        // Display the chosen date to app interface
        textViewDateTime.setText(formattedDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (ConfirmDateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement ConfirmHintDialogListener", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback.onPositiveDialogClick(new ChooseTimeDialogFragment());
        callback = null;

    }
    public interface ConfirmDateDialogListener {
        void onPositiveDialogClick(DialogFragment dialog);
        void onNegativeDialogClick(DialogFragment dialog);

    }
}
