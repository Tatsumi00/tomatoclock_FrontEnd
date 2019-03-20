package com.njupt.qmj.tomatotodo.Class;

public class Todo {
    private String todoName;
    private String todoIntro;
    private int imageId;

    public Todo(String todoName, String todoIntro, int imageId){
        this.todoName = todoName;
        this.todoIntro = todoIntro;
        this.imageId = imageId;
    }

    public String getTodoName(){
        return todoName;
    }

    public String getTodoIntro(){
        return todoIntro;
    }

    public int getImageId() {
        return imageId;
    }


}
