package ru.job4j.exam.models;

import java.util.List;
import java.util.Objects;

public class Question {
    private int id;
    private String name;
    private String desc;
    private int answer;
    private List<Option> options;
    private int position;
    private int rigthAnswer;

    public Question(String name, String desc, int answer, List<Option> options, int position, int rigthAnswer) {
        this.name = name;
        this.desc = desc;
        this.answer = answer;
        this.options = options;
        this.position = position;
        this.rigthAnswer = rigthAnswer;
    }

    public Question(int id, String name, String desc, int answer, List<Option> options, int position, int rigthAnswer) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.answer = answer;
        this.options = options;
        this.position = position;
        this.rigthAnswer = rigthAnswer;
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

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRigthAnswer() {
        return rigthAnswer;
    }

    public void setRigthAnswer(int rigthAnswer) {
        this.rigthAnswer = rigthAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id &&
                position == question.position &&
                rigthAnswer == question.rigthAnswer &&
                Objects.equals(name, question.name) &&
                Objects.equals(desc, question.desc) &&
                Objects.equals(answer, question.answer) &&
                Objects.equals(options, question.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, answer, options, position, rigthAnswer);
    }

    public void addOption (Option option){
        options.add(option);
    }
}
