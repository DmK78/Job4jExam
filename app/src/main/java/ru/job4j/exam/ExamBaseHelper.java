package ru.job4j.exam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.Data.Exam;

import static android.content.ContentValues.TAG;

public class ExamBaseHelper extends SQLiteOpenHelper {
    private static ExamBaseHelper instance;
    public static final String DB = "exams.db";
    public static final int VERSION = 32;

    public static ExamBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ExamBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private ExamBaseHelper(Context context) {
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
        List <Exam> result = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                ExamDbSchema.ExamTable.NAME,
                null, null, null,
                null, null, null
        );
        try{
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

        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


}