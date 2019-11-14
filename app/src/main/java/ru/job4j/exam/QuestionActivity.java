package ru.job4j.exam;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class QuestionActivity extends BaseActivity implements ConfirmHintDialog.ConfirmHintDialogListener {
    @Override
    public Fragment loadFrg() {
        return new QuestionFragment();
    }



    @Override
    public void onPositiveHintDialogClick(DialogFragment dialog, int id, String question) {
        Intent intent = new Intent(getApplicationContext(), HintActivity.class);
        intent.putExtra(QuestionFragment.HINT_FOR, id);
        intent.putExtra(QuestionFragment.QUESTION, question);
        startActivity(intent);
    }

    @Override
    public void onNegativeHintDialogClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), "Молодец!!!", Toast.LENGTH_SHORT).show();
    }
}
