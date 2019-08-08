package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExamsActivity extends AppCompatActivity implements ConfirmDeleteAllItemsDialog.ConfirmDeleteAllItemsDialogListener {
    private RecyclerView recycler;
    ExamAdapter adapter;
    private static List<Exam> exams = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_exams, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ExamActivity.class);
                startActivity(intent);
                return true;
            case R.id.delete_item:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
                DialogFragment dialog = new ConfirmDeleteAllItemsDialog();
                dialog.show(getSupportFragmentManager(), "dialog_tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exams);
        recycler = findViewById(R.id.examsRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (savedInstanceState == null) { //проверяем, что активность была воссоздана не после поворота экрана
            // если да, то создаем экзамен с ответами
            Intent intent = getIntent();
            if (intent.getBooleanExtra(ResultActivity.HAS_SAVED, false)) {
                String date = intent.getStringExtra(ResultActivity.CURRENT_DATE);
                float percent = intent.getFloatExtra(ResultActivity.PERCENT_OF_RIGHT_ANSWERS, 0);
                ArrayList<Integer> userChoices = intent.getIntegerArrayListExtra(ExamActivity.USER_CHOICES);
                exams.add(new Exam("result", date, (int) percent, userChoices));
            }
        }

        adapter = new ExamAdapter(this, exams);
        this.recycler.setAdapter(adapter);
    }

    @Override
    public void onPositiveDelItemsDialogClick(DialogFragment dialog) {
        exams.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNegativeDelItemsDialogClick(DialogFragment dialog) {
    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamHolder> {
        private List<Exam> exams;
        private LayoutInflater inflater;

        ExamAdapter(Context context, List<Exam> exams) {
            this.inflater = LayoutInflater.from(context);
            this.exams = exams;
        }

        @Override
        public ExamHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.info_exam, viewGroup, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(ExamAdapter.ExamHolder examHolder, int i) {
            Exam exam = exams.get(i);
            examHolder.textViewInfo.setText(exam.getName());
            examHolder.textViewDate.setText(exam.getTime());
            examHolder.textViewResult.setText(String.valueOf(exam.getResult()) + " %");
        }

        @Override
        public int getItemCount() {
            return exams.size();
        }

        public class ExamHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textViewInfo, textViewDate, textViewResult;

            ExamHolder(View view) {
                super(view);
                //this.view = itemView;
                textViewInfo = view.findViewById(R.id.textvViewInfo);
                textViewResult = view.findViewById(R.id.textViewResult);
                textViewDate = view.findViewById(R.id.textViewDate);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Exam exam = exams.get(getAdapterPosition());
                Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
                intent.putIntegerArrayListExtra(ExamActivity.USER_CHOICES, exam.getUserCoices());
                startActivity(intent);
            }
        }
    }
}
