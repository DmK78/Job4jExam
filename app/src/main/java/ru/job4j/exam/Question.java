package ru.job4j.exam;

import java.util.List;

public class Question {
    private int id;
    private String text;
    private List<Option> options;
    private int answer;
    private int userChoise;

    public Question(int id, String text, List<Option> options, int answer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.userChoise = -1;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getUserChoise() {
        return userChoise;
    }

    public void setUserChoise(int userChoise) {
        this.userChoise = userChoise;
    }
}
