package com.csvdatanatwest.natwestcsvdata.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    private Long rollNumber;
    private String name;
    private int science;
    private int maths;
    private int english;
    private int computer;
    private String eligibility = "ToBeChecked";
}
