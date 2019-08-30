package ru.job4j.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private SQLiteDatabase store;
    private ExamsCore examsCore = ExamsCore.getInstance();
    private ExamAdapter adapter;
    private List<Exam> exams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams, container, false);
        setHasOptionsMenu(true);
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
        inflater.inflate(R.menu.exams, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_exam:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.list, new ExamAddFragment())
                        .addToBackStack(null)
                        .commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                                examsCore.setCurrentExamTempName(exam.getName());
                                examsCore.setCurrentExamTempId(exam.getId());
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Fragment fragment = new ExamUpdateFragment();
                                fm.beginTransaction()
                                        .replace(R.id.list, fragment)
                                        .addToBackStack(null)
                                        .commit();
                                //notifyDataSetChanged();
                            }
                    );

            holder.view.findViewById(R.id.delete)
                    .setOnClickListener(
                            btn -> {

                                examsCore.deleteExamFromDB(exam);
                                exams.remove(exam);
                                //notifyItemRemoved(i);
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
}
