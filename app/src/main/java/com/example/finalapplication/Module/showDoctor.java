package com.example.finalapplication.Module;

public class showDoctor {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title , String id) {
        this.id=id;this.title = title;
    }

    String title;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public showDoctor(String title) {
        this.title = title;
    }
}
