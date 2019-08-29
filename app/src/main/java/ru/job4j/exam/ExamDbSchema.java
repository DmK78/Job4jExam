package ru.job4j.exam;

import android.provider.BaseColumns;

public class ExamDbSchema {
    public static final class ExamTable {
        public static final String NAME = "exams";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ExamTable.NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExamTable.Cols.TITLE + " TEXT, " +
                ExamTable.Cols.DESC + " TEXT, " +
                ExamTable.Cols.RESULT + " TEXT, " +
                ExamTable.Cols.DATE + " TEXT" + ")";


        public static final class Cols {
            public static final String TITLE = "title";
            public static final String DESC = "desc";
            public static final String RESULT = "result";
            public static final String DATE = "date";
        }
    }

    public static final class QuestionsTable {
        public static final String NAME = "questions";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                QuestionsTable.NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Cols.NAME + " TEXT, " +
                Cols.DESC + " TEXT, " +
                Cols.EXAM_ID + " INTEGER, " +
                Cols.ANSWER_ID + " INTEGER, " +
                Cols.POSITION + " INTEGER, " +
                Cols.RIGHT_ANSWER + " INTEGER" + ")";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String DESC = "desc";
            public static final String EXAM_ID = "exam_id";
            public static final String ANSWER_ID = "answer_id";
            public static final String POSITION = "position";
            public static final String RIGHT_ANSWER = "right_answer";
        }
    }

    public static final class OptionsTable {
        public static final String NAME = "options";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                OptionsTable.NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Cols.NAME + " TEXT, " +
                Cols.QUESTION_ID + " INTEGER" + ")";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String QUESTION_ID = "question_id";
        }
    }

    public static final class AnswersTable {
        public static final String NAME = "answers";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                AnswersTable.NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Cols.OPTION_ID + " INTEGER, " +
                Cols.QUESTION_ID + " INTEGER, " +
                Cols.EXAM_ID + " INTEGER" + ")";

        public static final class Cols {
            public static final String OPTION_ID = "option_id";
            public static final String QUESTION_ID = "question_id";
            public static final String EXAM_ID = "exam_id";
        }
    }
}