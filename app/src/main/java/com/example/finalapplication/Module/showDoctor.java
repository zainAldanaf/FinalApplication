package com.example.finalapplication.Module;

public class showDoctor {
    public String id;
    String title;

    public showDoctor(String title, String id) {
        this.title = title;
        this.id = id;
    }
    private showDoctor(){}

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {

        return title;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

}
