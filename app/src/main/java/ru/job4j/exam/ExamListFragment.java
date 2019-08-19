package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExamListFragment extends Fragment {
    private RecyclerView recycler;
    private SQLiteDatabase store;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams, container, false);
        this.recycler = view.findViewById(R.id.examsRecyclerView);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.store = new ExamBaseHelper(this.getContext()).getWritableDatabase();
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Exam> exams = new ArrayList<Exam>();
        Cursor cursor = this.store.query(
                ExamDbSchema.ExamTable.NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exams.add(new Exam(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    System.currentTimeMillis(),
                    100,null
            ));
            cursor.moveToNext();
        }
        cursor.close();
        this.recycler.setAdapter(new ExamAdapter(getContext(),exams));
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
            holder.view.findViewById(R.id.edit)
                    .setOnClickListener(
                            btn -> {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Fragment fragment = new ExamUpdateFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", exam.getId());
                                bundle.putString("name", exam.getName());
                                fragment.setArguments(bundle);
                                fm.beginTransaction()
                                        .replace(R.id.list, fragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                    );

            holder.view.findViewById(R.id.delete)
                    .setOnClickListener(
                            btn -> {
                                store.delete(ExamDbSchema.ExamTable.NAME, "id = ?", new String[] { String.valueOf(exam.getId()) });
                                exams.remove(exam);
                                notifyItemRemoved(i);
                            }
                    );
        }

        @Override
        public int getItemCount() {
            return this.exams.size();
        }

        public class ExamHolder extends RecyclerView.ViewHolder {
            TextView text;
            ImageView imageViewDelete, imageViewEdit;
            View view;

            public ExamHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.q_text);
                imageViewDelete=itemView.findViewById(R.id.delete);
                imageViewEdit=itemView.findViewById(R.id.edit);
                this.view = itemView;

            }
        }
    }
}
