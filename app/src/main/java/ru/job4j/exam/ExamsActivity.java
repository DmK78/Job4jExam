package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExamsActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private static List<Exam> exams = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exams);
        recycler = findViewById(R.id.examsRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        if (intent.getBooleanExtra(ResultActivity.HAS_SAVED, false)) {
            String date = intent.getStringExtra(ResultActivity.CURRENT_DATE);
            float percent = intent.getFloatExtra(ResultActivity.PERCENT_OF_RIGHT_ANSWERS, 0);
            ArrayList<Integer> userChoices = intent.getIntegerArrayListExtra(ExamActivity.USER_CHOICES);
            exams.add(new Exam("result", date, (int) percent, userChoices));
        }
        this.recycler.setAdapter(new ExamAdapter(this, exams));
    }

    public void addNewExam(View view) {
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamHolder> {
        private List<Exam> exams;
        private LayoutInflater inflater;
        private ItemClickListener mClickListener;

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
            examHolder.textViewResult.setText(String.valueOf(exam.getResult()));
        }

        @Override
        public int getItemCount() {
            return exams.size();
        }

        public class ExamHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
                //if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
                Exam exam = exams.get(getAdapterPosition());
                Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
                intent.putIntegerArrayListExtra(ExamActivity.USER_CHOICES, exam.getUserCoices());
                startActivity(intent);
            }
        }

        Exam getItem(int id) {
            return exams.get(id);
        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
