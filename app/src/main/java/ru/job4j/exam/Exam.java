package ru.job4j.exam;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    private int id;
    private String name;
    private long time;
    private int result;
    private ArrayList<Integer> userCoices;


    public Exam(int id, String name, long time, int result, ArrayList<Integer> userCoices) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.result = result;
        this.userCoices = userCoices;
    }

    public ArrayList<Integer> getUserCoices() {
        return userCoices;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
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
