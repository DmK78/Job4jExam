package ru.job4j.exam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ConfirmHintDialog extends DialogFragment {
    private ConfirmHintDialogListener callback;
    private int id;
    private String question;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Показать подсказку?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onPositiveHintDialogClick(ConfirmHintDialog.this, id, question);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNegativeHintDialogClick(ConfirmHintDialog.this);
                    }
                })
                .create();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (ConfirmHintDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement ConfirmHintDialogListener", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public static ConfirmHintDialog of (int id, String question){
        ConfirmHintDialog confirmHintDialog = new ConfirmHintDialog();
        confirmHintDialog.id=id;
        confirmHintDialog.question= question;
        return confirmHintDialog;
    }

    public interface ConfirmHintDialogListener {
        void onPositiveHintDialogClick(DialogFragment dialog, int id, String question);

        void onNegativeHintDialogClick(DialogFragment dialog);
    }
}

