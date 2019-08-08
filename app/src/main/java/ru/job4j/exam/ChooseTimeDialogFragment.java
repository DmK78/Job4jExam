package ru.job4j.exam;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class ChooseTimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private ConfirmTimeDialogListener callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textViewDateTime = getActivity().findViewById(R.id.textViewDateTime);
        StringBuilder text = new StringBuilder();
        text.append(textViewDateTime.getText());
        text.append(" ").append(hourOfDay).append(":").append(minute);
        textViewDateTime.setText(text.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (ConfirmTimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement ConfirmHintDialogListener", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface ConfirmTimeDialogListener {
        void onPositiveDialogClick(DialogFragment dialog);
        void onNegativeDialogClick(DialogFragment dialog);
    }
}
