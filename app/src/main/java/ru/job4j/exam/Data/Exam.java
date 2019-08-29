package ru.job4j.exam.Data;

import java.util.List;
import java.util.Objects;

public class Exam {
    private int id;
    private String name;
    private String desc;
    private String result;
    private String date;
    private List<Question> questions;

    public Exam(String name, String desc, String result, String date, List<Question> questions) {
        this.name = name;
        this.desc = desc;
        this.result = result;
        this.date = date;
        this.questions = questions;
    }

    public Exam(int id, String name, String desc, String result, String date, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.result = result;
        this.date = date;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id &&
                Objects.equals(name, exam.name) &&
                Objects.equals(desc, exam.desc) &&
                Objects.equals(result, exam.result) &&
                Objects.equals(date, exam.date) &&
                Objects.equals(questions, exam.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, result, date, questions);
    }
}