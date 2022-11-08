package com.webclient.app.model;


public class Student {
    private long id;

    private String name;

    private String birthDate;

    private Integer credits;
    
    private float averageGrade; 

    public String getName(){
        return name;
    }

    public String getBirthDate(){
        return birthDate;
    }

    public Integer getCredits() {
        return credits;
    }
}
