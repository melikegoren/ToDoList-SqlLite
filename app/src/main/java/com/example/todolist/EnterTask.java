package com.example.todolist;

public class EnterTask {
    private int id;
    private String task;

    public EnterTask(int id, String task) {
        this.id = id;
        this.task = task;

    }
    public EnterTask(){

    }

    @Override
    public String toString() {
        return  "  " + task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

}
