package ru.job4j.exam.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.models.Option;
import ru.job4j.exam.models.Question;

public class ExamsCore {

    private Context context;
    private ExamBaseHelper databaseHelper;
    private final List<Exam> exams = Arrays.asList(new Exam("Exam 1", "", "", "", Arrays.asList(
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
    )), new Exam("Exam 2", "", "", "", Arrays.asList(
            new Question(
                    "Exam 2 question 1",
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
                    "Exam 2 question 2",
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
                    "Exam 2 question 3",
                    "", -1,
                    Arrays.asList(
                            new Option(1, "3.1"), new Option(2, "3.2"),
                            new Option(3, "3.3"), new Option(4, "3.4")
                    ), 2, 4
            )
    )));

    public void init(Context context) {
        this.context = context;
        databaseHelper = new ExamBaseHelper(context);
    }


    public List<Exam> loadExams() {
        List<Exam> result = new ArrayList<>();
        result.addAll(databaseHelper.getAllExamsWithoutQuestions());
        if (result.size() == 0) {
            for (Exam exam : exams) {
                saveExamToDb(exam);
            }
            result = databaseHelper.getAllExamsWithoutQuestions();
        }
        return result;
    }

    public void saveExamToDb(Exam exam) {
        databaseHelper.addExam(exam);
    }


    public Exam getExamFromDb(int id) {
        return databaseHelper.getExam(id);
    }

    public void updateExamToDb(Exam currentExam) {
        databaseHelper.updateExam(currentExam);

    }

    public void deleteExamFromDB(Exam exam) {
        databaseHelper.deleteExam(exam);
    }


    public boolean deleteAllExamsFromDb() {
        boolean result = false;
        if (databaseHelper.deleteAllExams()) {
            result = true;
        }
        return result;
    }

    public void saveQuestionToDb(int id, Question question) {
        databaseHelper.saveQuestionToDb(id, question);
    }

    public void clearExam(Exam exam) {
        databaseHelper.clearExam(exam);
    }
}
