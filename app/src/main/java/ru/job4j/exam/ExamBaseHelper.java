package ru.job4j.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExamBaseHelper extends SQLiteOpenHelper {
    public static final String DB = "exams.db";
    public static final int VERSION = 29;

    public ExamBaseHelper(Context context) {
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
}