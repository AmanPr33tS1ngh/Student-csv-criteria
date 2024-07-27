package com.csvdatanatwest.natwestcsvdata.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Criteria {

    @Id
    private Long id = 1L;
    private int maths = 90;
    private int science = 85;
    private int english = 75;
    private int computer = 95;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaths() {
        return maths;
    }

    public void setMaths(int maths) {
        this.maths = maths;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }
}
