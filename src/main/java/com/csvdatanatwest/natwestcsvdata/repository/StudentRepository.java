package com.csvdatanatwest.natwestcsvdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csvdatanatwest.natwestcsvdata.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
