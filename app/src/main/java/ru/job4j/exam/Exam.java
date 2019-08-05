package ru.job4j.exam;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    private int id = 1;
    private String name;
    private String time;
    private int result;
    private ArrayList<Integer> userCoices;

    public Exam(String name, String time, int result, ArrayList<Integer> userChoices) {
        this.id = id++;
        this.name = name;
        this.time = time;
        this.result = result;
        this.userCoices = userChoices;
    }

    public ArrayList<Integer> getUserCoices() {
        return userCoices;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", result=" + result +
                '}';
    }
}
