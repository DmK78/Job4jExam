package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import ru.job4j.exam.Data.Exam;

public class ExamListFragment extends Fragment {
    private RecyclerView recycler;
    @Inject
    ExamsCore examsCore;
    public static ExamAdapter adapter;
    private List<Exam> exams;


    public static final String EXAM_NAME = "examToUpdateName";
    public static final String EXAM_ID = "examToUpdateId";
    public static final String EXAM_UPDATING = "udatingExam";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams, container, false);
        App.getExamCore().injectTo(this);
        exams = examsCore.loadExams();
        this.recycler = view.findViewById(R.id.examsRecyclerView);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamAdapter(getContext(), exams);
        this.recycler.setAdapter(adapter);
        return view;
    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamHolder> {

        private final List<Exam> exams;
        private LayoutInflater inflater;


        public ExamAdapter(Context context, List<Exam> exams) {
            this.inflater = LayoutInflater.from(context);
            this.exams = exams;
        }


        @NonNull
        @Override
        public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = inflater.inflate(R.layout.info_exam, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int i) {
            Exam exam = exams.get(i);
            holder.text.setText(exam.getName());
            if (!exam.getResult().equals("")) {
                holder.result.setText(exam.getResult());
            } else {
                holder.result.setText("не пройден");
            }
            holder.date.setText(exam.getDate());
            holder.view.findViewById(R.id.edit)
                    .setOnClickListener(
                            btn -> {
                                Intent intent = new Intent(getContext(), QuestionActivity.class);
                                intent.putExtra(EXAM_ID, exam.getId());
                                startActivity(intent);
                            }
                    );

            holder.view.findViewById(R.id.restart)
                    .setOnClickListener(
                            btn -> {
                                DialogFragment dialog = new ConfirmRestartExamDialog();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ExamListFragment.EXAM_ID, exam.getId());
                                dialog.setArguments(bundle);
                                dialog.show(getFragmentManager(), "dialog_tag");
                            }
                    );
        }

        @Override
        public int getItemCount() {
            return this.exams.size();
        }

        public class ExamHolder extends RecyclerView.ViewHolder {
            TextView text, result, date;
            ImageView imageViewDelete, imageViewEdit;
            View view;

            public ExamHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.q_text);
                result = itemView.findViewById(R.id.result);
                date = itemView.findViewById(R.id.date);
                imageViewDelete = itemView.findViewById(R.id.restart);
                imageViewEdit = itemView.findViewById(R.id.edit);
                this.view = itemView;
            }
        }
    }
}