package ru.job4j.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import ru.job4j.exam.Data.Exam;
import ru.job4j.exam.Data.Option;
import ru.job4j.exam.Data.Question;

public class ExamsCore {
    private static ExamsCore instance;
    public boolean examUptading = false;
    private List<Exam> exams = new ArrayList<>();
    private SQLiteDatabase db;
    private Context context;
    private Exam currentExam;
    private int currentQuestionPosition;
    private String currentExamTempName;
    private ExamBaseHelper databaseHelper;

    private int currentExamTempId;
    private final List<Question> questions = Arrays.asList(
            new Question(
                    "How many primitive variables does Java have?",
                    "",
                    -1,
                    Arrays.asList(
                            new Option(1, "1.1"),
                            new Option(2, "1.2"),
                            new Option(3, "1.3"), new Option(4, "1.4")
                    ),
                    0, 4
            ),
            new Question(
                    "What is Java Virtual Machine?",
                    "",
                    -1,
                    Arrays.asList(
                            new Option(1, "2.1"),
                            new Option(2, "2.2"),
                            new Option(3, "2.3"),
                            new Option(4, "2.4")
                    ), 1, 4
            ),
            new Question(
                    "What is happen if we try unboxing null?",
                    "", -1,
                    Arrays.asList(
                            new Option(1, "3.1"), new Option(2, "3.2"),
                            new Option(3, "3.3"), new Option(4, "3.4")
                    ), 2, 4
            )
    );

    private ExamsCore() {
    }

    public static ExamsCore getInstance() {
        if (instance == null) {
            instance = new ExamsCore();
        }
        return instance;
    }


    public void init(Context context) {
        this.context = context;
        databaseHelper = ExamBaseHelper.getInstance(context);
    }



    public List<Exam> getAllExams() {
        return exams;
    }

    public List<Exam> loadExamsFromDb() {
        exams.clear();
        List<Exam> result = databaseHelper.getAllExamsNames();
            /*this.db = new ExamBaseHelper(context).getReadableDatabase();
            Cursor cursor = this.db.query(
                    ExamDbSchema.ExamTable.NAME,
                    null, null, null,
                    null, null, null
            );
            if (cursor.moveToFirst()) {
                do {
                    exams.add(new Exam(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("desc")),
                            cursor.getString(cursor.getColumnIndex("result")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            new ArrayList<>()
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();*/
        return exams;
    }

    public void saveExamToDb() {
        exams.add(currentExam);
        long questionId;
        long examId;
        long optionId;

      /*  this.db = new ExamBaseHelper(context).getWritableDatabase();
        ContentValues valueExam = new ContentValues();
        valueExam.put(ExamDbSchema.ExamTable.Cols.TITLE, currentExam.getName());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DESC, currentExam.getDesc());
        valueExam.put(ExamDbSchema.ExamTable.Cols.RESULT, currentExam.getResult());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DATE, currentExam.getDate());
        examId = db.insert(ExamDbSchema.ExamTable.NAME, null, valueExam);

        for (Question question : currentExam.getQuestions()) {
            ContentValues valueQuestion = new ContentValues();
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.NAME, question.getName());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.DESC, question.getDesc());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.EXAM_ID, examId);
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.ANSWER_ID, question.getAnswer());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.POSITION, question.getPosition());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.RIGHT_ANSWER, question.getRigthAnswer());
            questionId = db.insert(ExamDbSchema.QuestionsTable.NAME, null, valueQuestion);

            for (Option option : question.getOptions()) {
                ContentValues valueOption = new ContentValues();
                valueOption.put(ExamDbSchema.OptionsTable.Cols.NAME, option.getText());
                valueOption.put(ExamDbSchema.OptionsTable.Cols.QUESTION_ID, questionId);
                optionId = db.insert(ExamDbSchema.OptionsTable.NAME, null, valueOption);
            }
        }
        db.close();*/
    }

    public void countResult() {
        int rightAnswers = 0;
        for (Question question : currentExam.getQuestions()) {
            if (question.getRigthAnswer() == question.getAnswer() + 1) {
                rightAnswers++;
            }
        }
        float result = (float) rightAnswers / (float) questions.size() * 100;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        currentExam.setResult(String.valueOf((int) result + " %"));
        currentExam.setDate(currentDateandTime);
    }

    public Exam getCurrentExam() {
        return currentExam;
    }

    public void setCurrentExam(Exam currentExam) {
        this.currentExam = currentExam;
    }

    public List<Question> getNewQuestions() {
        for (Question question : questions) {
            question.setAnswer(-1);
        }
        return questions;
    }

    public Exam getExamFromDb(int id) {
        Exam result=null;
        List<Question> resultQuestions = new ArrayList<>();
     /*   this.db = new ExamBaseHelper(context.getApplicationContext()).getReadableDatabase();
        String selectionExam = "id =?";
        String[] selectionArgsExam = new String[]{String.valueOf(id)};
        Cursor cursorExam = this.db.query(
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

        Cursor cursorQuestion = this.db.query(
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

                Cursor cursorOption = this.db.query(
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
        db.close();*/

       // result.setQuestions(resultQuestions);
        return result;
    }

    public void setCurrentExamTempName(String currentExamTempName) {
        this.currentExamTempName = currentExamTempName;
    }

    public String getCurrentExamTempName() {
        return currentExamTempName;
    }

    public void setCurrentExamId(int id) {
        currentExam.setId(id);
    }

    public int getCurrentExamId() {
        return currentExam.getId();
    }

    public void updateExamToDb() {
        long questionId;
        long examId;

      /*  this.db = new ExamBaseHelper(context).getWritableDatabase();
        ContentValues valueExam = new ContentValues();
        valueExam.put(ExamDbSchema.ExamTable.Cols.TITLE, currentExam.getName());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DESC, currentExam.getDesc());
        valueExam.put(ExamDbSchema.ExamTable.Cols.RESULT, currentExam.getResult());
        valueExam.put(ExamDbSchema.ExamTable.Cols.DATE, currentExam.getDate());
        examId = db.update(ExamDbSchema.ExamTable.NAME, valueExam, "id =?",
                new String[]{String.valueOf(currentExam.getId())});
        for (Question question : currentExam.getQuestions()) {
            ContentValues valueQuestion = new ContentValues();
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.NAME, question.getName());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.DESC, question.getDesc());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.EXAM_ID, currentExam.getId());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.ANSWER_ID, question.getAnswer());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.POSITION, question.getPosition());
            valueQuestion.put(ExamDbSchema.QuestionsTable.Cols.RIGHT_ANSWER, question.getRigthAnswer());
            questionId = db.update(ExamDbSchema.QuestionsTable.NAME, valueQuestion, "id =?",
                    new String[]{String.valueOf(question.getId())});

            for (Option option : question.getOptions()) {
                ContentValues valueOption = new ContentValues();
                valueOption.put(ExamDbSchema.OptionsTable.Cols.NAME, option.getText());
                valueOption.put(ExamDbSchema.OptionsTable.Cols.QUESTION_ID, question.getId());
                long optionId = db.update(ExamDbSchema.OptionsTable.NAME, valueOption, "id =?",
                        new String[]{String.valueOf(option.getId())});
                examUptading = false;
            }
        }
        db.close();*/
    }

    public int getCurrentExamTempId() {
        return currentExamTempId;
    }

    public void setCurrentExamTempId(int currentExamTempId) {
        this.currentExamTempId = currentExamTempId;
    }

    public void deleteExamFromDB(Exam exam) {
       /* this.db = new ExamBaseHelper(context).getWritableDatabase();
        exam = getExamFromDb(exam.getId());
        db = new ExamBaseHelper(context).getWritableDatabase();
        db.delete(ExamDbSchema.ExamTable.NAME, "id = ?", new String[]{String.valueOf(exam.getId())});
        for (Question question : exam.getQuestions()) {
            db.delete(ExamDbSchema.QuestionsTable.NAME, "id = ?", new String[]{String.valueOf(question.getId())});
            for (Option option : question.getOptions()) {
                db.delete(ExamDbSchema.OptionsTable.NAME, "id = ?", new String[]{String.valueOf(option.getId())});
            }
        }
        db.close();*/
    }

    public int getCurrentQuestionPosition() {
        return currentQuestionPosition;
    }

    public void setCurrentQuestionPosition(int currentQuestionPosition) {
        this.currentQuestionPosition = currentQuestionPosition;
    }

    public boolean deleteAllExamsFromDb(){
        boolean result=false;
    /*    db = new ExamBaseHelper(context).getWritableDatabase();
        db.execSQL("delete from "+ ExamDbSchema.ExamTable.NAME);
        db.execSQL("delete from "+ ExamDbSchema.QuestionsTable.NAME);
        db.execSQL("delete from "+ ExamDbSchema.OptionsTable.NAME);
        db.close();
        db = new ExamBaseHelper(context).getReadableDatabase();
        Cursor cursor = this.db.query(
                ExamDbSchema.ExamTable.NAME,
                null, null, null,
                null, null, null
        );
        if(!cursor.moveToFirst()){
            cursor = this.db.query(
                    ExamDbSchema.QuestionsTable.NAME,
                    null, null, null,
                    null, null, null
            );
            if (!cursor.moveToFirst()){
                cursor = this.db.query(
                        ExamDbSchema.OptionsTable.NAME,
                        null, null, null,
                        null, null, null
                );
                if(!cursor.moveToFirst()){
                    result= true;
                    exams.clear();
                    ExamListFragment.adapter.notifyDataSetChanged();
                }
            }
        }*/
return result;
    }
}
