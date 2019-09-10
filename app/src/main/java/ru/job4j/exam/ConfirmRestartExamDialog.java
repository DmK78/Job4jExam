package ru.job4j.exam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class ConfirmRestartExamDialog extends DialogFragment {
    private ConfirmRestartExamDialogListener callback;
    private int examId;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            examId=getArguments().getInt(ExamListFragment.EXAM_ID,-1);
        }

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Пересдать экзамен?")

                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNegativeRestartExamDialogClick(ConfirmRestartExamDialog.this);
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onPositiveRestartExamDialogClick(ConfirmRestartExamDialog.this,examId);
                    }
                })
                .create();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (ConfirmRestartExamDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement ConfirmHintDialogListener", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface ConfirmRestartExamDialogListener {
        void onPositiveRestartExamDialogClick(DialogFragment dialog, int id);
        void onNegativeRestartExamDialogClick(DialogFragment dialog);
    }
}
