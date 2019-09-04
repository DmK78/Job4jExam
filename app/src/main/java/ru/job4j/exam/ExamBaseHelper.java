package ru.job4j.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.Data.Exam;
import ru.job4j.exam.Data.Option;
import ru.job4j.exam.Data.Question;

public class ExamBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase dbRead = getReadableDatabase();
    SQLiteDatabase dbWrite = getWritableDatabase();

    public static final String DB = "exams.db";
    public static final int VERSION = 33;

    ExamBaseHelper(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExamDbSchema.ExamTable.CREATE_TABLE);
        db.execSQL(ExamDbSchema.QuestionsTable.CREATE_TABLE);
        db.execSQL(ExamDbSchema.OptionsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ExamDbSchema.ExamTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExamDbSchema.QuestionsTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExamDbSchema.OptionsTable.NAME);
        onCreate(db);
    }

    public List<Exam> getAllExamsNames() {
        List<Exam> result = new ArrayList<>();
        Cursor cursor = dbRead.query(
                ExamDbSchema.ExamTable.NAME,
                null, null, null,
                null, null, null
        );
        if (cursor.moveToFirst()) {
            do {
                result.add(new Exam(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("desc")),
                        cursor.getString(cursor.getColumnIndex("result")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        new ArrayList<>()
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addExam(Exam exam) {
        long questionId;
        long examId;
        long optionId;

        ContentValues valueExam = new ContentValues();
        valueExam.put(ExamDbSchema.ExamTable.Cols.TITLE, exam.getName());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DESC, exam.getDesc());
        valueExam.put(ExamDbSchema.ExamTable.Cols.RESULT, exam.getResult());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DATE, exam.getDate());
        examId = dbWrite.insert(ExamDbSchema.ExamTable.NAME, null, valueExam);
        for (Question question : exam.getQuestions()) {
            ContentValues valueQuestion = new ContentValues();
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.NAME, question.getName());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.DESC, question.getDesc());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.EXAM_ID, examId);
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.ANSWER_ID, question.getAnswer());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.POSITION, question.getPosition());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.RIGHT_ANSWER, question.getRigthAnswer());
            questionId = dbWrite.insert(ExamDbSchema.QuestionsTable.NAME, null, valueQuestion);
            for (Option option : question.getOptions()) {
                ContentValues valueOption = new ContentValues();
                valueOption.put(ExamDbSchema.OptionsTable.Cols.NAME, option.getText());
                valueOption.put(ExamDbSchema.OptionsTable.Cols.QUESTION_ID, questionId);
                dbWrite.insert(ExamDbSchema.OptionsTable.NAME, null, valueOption);
            }
        }
    }


    public Exam getExam(int id) {
        Exam result = null;
        List<Question> resultQuestions = new ArrayList<>();
        String selectionExam = "id =?";
        String[] selectionArgsExam = new String[]{String.valueOf(id)};
        Cursor cursorExam = dbRead.query(
                ExamDbSchema.ExamTable.NAME,
                null, selectionExam, selectionArgsExam,
                null, null, null
        );
        cursorExam.moveToFirst();
        result = new Exam(
                cursorExam.getInt(cursorExam.getColumnIndex("id")),
                cursorExam.getString(cursorExam.getColumnIndex(ExamDbSchema.ExamTable.Cols.TITLE)),
                cursorExam.getString(cursorExam.getColumnIndex(ExamDbSchema.ExamTable.Cols.DESC)),
                cursorExam.getString(cursorExam.getColumnIndex(ExamDbSchema.ExamTable.Cols.RESULT)),
                cursorExam.getString(cursorExam.getColumnIndex(ExamDbSchema.ExamTable.Cols.DATE)),
                new ArrayList<>());
        cursorExam.close();
        String selectionQuestion = ExamDbSchema.QuestionsTable.Cols.EXAM_ID + " =?";
        String[] selectionArgsQuestion = new String[]{String.valueOf(id)};
        Cursor cursorQuestion = dbRead.query(
                ExamDbSchema.QuestionsTable.NAME,
                null, selectionQuestion, selectionArgsQuestion,
                null, null, null
        );
        if (cursorQuestion.moveToFirst()) {
            do {
                Question question = new Question(
                        cursorQuestion.getInt(cursorQuestion.getColumnIndex("id")),
                        cursorQuestion.getString(cursorQuestion.getColumnIndex(ExamDbSchema.QuestionsTable.Cols.NAME)),
                        cursorQuestion.getString(cursorQuestion.getColumnIndex(ExamDbSchema.QuestionsTable.Cols.DESC)),
                        cursorQuestion.getInt(cursorQuestion.getColumnIndex(ExamDbSchema.QuestionsTable.Cols.ANSWER_ID)),
                        new ArrayList<Option>(),
                        cursorQuestion.getInt(cursorQuestion.getColumnIndex(ExamDbSchema.QuestionsTable.Cols.POSITION)),
                        cursorQuestion.getInt(cursorQuestion.getColumnIndex(ExamDbSchema.QuestionsTable.Cols.RIGHT_ANSWER)));
                String selectionOption = ExamDbSchema.OptionsTable.Cols.QUESTION_ID + "=?";
                String[] selectionArgsOption = new String[]{String.valueOf(question.getId())};
                Cursor cursorOption = dbRead.query(
                        ExamDbSchema.OptionsTable.NAME,
                        null, selectionOption, selectionArgsOption,
                        null, null, null
                );
                if (cursorOption.moveToFirst()) {
                    do {
                        Option option = new Option(
                                cursorOption.getInt(cursorOption.getColumnIndex("id")),
                                cursorOption.getString(cursorOption.getColumnIndex(ExamDbSchema.OptionsTable.Cols.NAME))
                        );
                        question.addOption(option);

                    } while (cursorOption.moveToNext());
                }
                cursorOption.close();
                resultQuestions.add(question);
            } while (cursorQuestion.moveToNext());
        }
        cursorQuestion.close();
        result.setQuestions(resultQuestions);
        return result;
    }

    public void updateExam(Exam currentExam) {
        long questionId;
        long examId;
        ContentValues valueExam = new ContentValues();
        valueExam.put(ExamDbSchema.ExamTable.Cols.TITLE, currentExam.getName());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DESC, currentExam.getDesc());
        valueExam.put(ExamDbSchema.ExamTable.Cols.RESULT, currentExam.getResult());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DATE, currentExam.getDate());
        examId = dbWrite.update(ExamDbSchema.ExamTable.NAME, valueExam, "id =?",
                new String[]{String.valueOf(currentExam.getId())});
        for (Question question : currentExam.getQuestions()) {
            ContentValues valueQuestion = new ContentValues();
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.NAME, question.getName());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.DESC, question.getDesc());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.EXAM_ID, currentExam.getId());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.ANSWER_ID, question.getAnswer());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.POSITION, question.getPosition());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.RIGHT_ANSWER, question.getRigthAnswer());
            questionId = dbWrite.update(ExamDbSchema.QuestionsTable.NAME, valueQuestion, "id =?",
                    new String[]{String.valueOf(question.getId())});
        }
    }

    public void deleteExam(Exam exam) {
        dbWrite.delete(ExamDbSchema.ExamTable.NAME, "id = ?", new String[]{String.valueOf(exam.getId())});
        for (Question question : exam.getQuestions()) {
            dbWrite.delete(ExamDbSchema.QuestionsTable.NAME, "id = ?", new String[]{String.valueOf(question.getId())});
            for (Option option : question.getOptions()) {
                dbWrite.delete(ExamDbSchema.OptionsTable.NAME, "id = ?", new String[]{String.valueOf(option.getId())});
            }
        }
    }

    public boolean deleteAllExams() {
        boolean result = false;
        dbWrite.execSQL("delete from " + ExamDbSchema.ExamTable.NAME);
        dbWrite.execSQL("delete from " + ExamDbSchema.QuestionsTable.NAME);
        dbWrite.execSQL("delete from " + ExamDbSchema.OptionsTable.NAME);
        Cursor cursor = dbRead.query(
                ExamDbSchema.ExamTable.NAME,
                null, null, null,
                null, null, null
        );
        if (!cursor.moveToFirst()) {
            cursor = dbRead.query(
                    ExamDbSchema.QuestionsTable.NAME,
                    null, null, null,
                    null, null, null
            );
            if (!cursor.moveToFirst()) {
                cursor = dbRead.query(
                        ExamDbSchema.OptionsTable.NAME,
                        null, null, null,
                        null, null, null
                );
                if (!cursor.moveToFirst()) {
                    result = true;
                    ExamListFragment.adapter.notifyDataSetChanged();
                }
            }
        }
        return result;
    }
}
