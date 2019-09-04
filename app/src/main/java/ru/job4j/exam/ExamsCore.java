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
        databaseHelper = new ExamBaseHelper(context);

    }


    public List<Exam> loadExamsFromDb() {
        List<Exam> result = new ArrayList<>();
        result.addAll(databaseHelper.getAllExamsNames());
        return result;
    }

    public void saveExamToDb(Exam exam) {
        databaseHelper.addExam(exam);
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
        return databaseHelper.getExam(id);
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

    public void updateCurrentExamToDb() {
        databaseHelper.updateExam(currentExam);
        examUptading = false;
    }

    public int getCurrentExamTempId() {
        return currentExamTempId;
    }

    public void setCurrentExamTempId(int currentExamTempId) {
        this.currentExamTempId = currentExamTempId;
    }

    public void deleteExamFromDB(Exam exam) {
        databaseHelper.deleteExam(exam);
    }

    public int getCurrentQuestionPosition() {
        return currentQuestionPosition;
    }

    public void setCurrentQuestionPosition(int currentQuestionPosition) {
        this.currentQuestionPosition = currentQuestionPosition;
    }

    public boolean deleteAllExamsFromDb() {
        boolean result = false;
        if (databaseHelper.deleteAllExams()) {
            result = true;
            //exams.clear();
        }
        return result;
    }
}
