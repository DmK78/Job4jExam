package ru.job4j.exam.Data;

import java.util.Objects;

public class Answer {
    private int id;
    private int answer;

    public Answer(int id, int answer) {
        this.id = id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return id == answer1.id &&
                answer == answer1.answer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer);
    }
}
