package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.job4j.exam.Data.Exam;

public class ExamListFragment extends Fragment {
    private RecyclerView recycler;
    private ExamsCore examsCore = ExamsCore.getInstance();
    public static ExamAdapter adapter;
    private List<Exam> exams;

    private OnExamListButtonClickListener callback;

    public static final String EXAM_NAME = "examToUpdateName";
    public static final String EXAM_ID = "examToUpdateId";
    public static final String EXAM_UPDATING = "udatingExam";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams, container, false);
        setHasOptionsMenu(false);
        exams = examsCore.loadExamsFromDb();
        this.recycler = view.findViewById(R.id.examsRecyclerView);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamAdapter(getContext(), exams);
        this.recycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_exams, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                callback.onMenuAddExamButtonClicked();
                //Intent intent = new Intent(getContext(), ExamSetNameFragment.class);
                //startActivity(intent);

                return true;
            case R.id.delete_item:
                if (exams.size() > 0) {
                    DialogFragment dialog = new ConfirmDeleteAllItemsDialog();
                    dialog.show(getFragmentManager(), "dialog_tag");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnExamListButtonClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
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
            if ((i % 2) == 0) {
                holder.view.setBackgroundColor(Color.parseColor("#d8d8d8"));
            }
            holder.text.setText(exam.getName());
            holder.result.setText(exam.getResult());
            holder.date.setText(exam.getDate());

            holder.view.findViewById(R.id.edit)
                    .setOnClickListener(
                            btn -> {
                                callback.onEditExamButtonClicked(exam);
                                /*Intent intent = new Intent(getContext(), ExamSetNameFragment.class);
                                intent.putExtra(EXAM_NAME, exam.getName());
                                intent.putExtra(EXAM_ID, exam.getId());
                                intent.putExtra(EXAM_UPDATING, true);
                                //examsCore.setCurrentExamTempName(exam.getName());
                                //examsCore.setCurrentExamTempId(exam.getId());
                                startActivity(intent);*/
                            }
                    );

            holder.view.findViewById(R.id.delete)
                    .setOnClickListener(
                            btn -> {
                                exams.remove(exam);
                                examsCore.deleteExamFromDB(exam);
                                notifyDataSetChanged();
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
                imageViewDelete = itemView.findViewById(R.id.delete);
                imageViewEdit = itemView.findViewById(R.id.edit);
                this.view = itemView;

            }
        }
    }

    public void clearExams() {
        exams.clear();
        adapter.notifyDataSetChanged();
    }

    public interface OnExamListButtonClickListener {
        void onMenuAddExamButtonClicked();

        void onEditExamButtonClicked(Exam exam);


    }
}