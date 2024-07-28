package com.csvdatanatwest.natwestcsvdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Criteria {
    @Id
    private Long id = 1L;
    private int maths = 90;
    private int science = 85;
    private int english = 75;
    private int computer = 95;

    public Criteria() {}

    public Criteria(int maths, int science, int english, int computer) {
        this.maths = maths;
        this.science = science;
        this.english = english;
        this.computer = computer;
    }
}
