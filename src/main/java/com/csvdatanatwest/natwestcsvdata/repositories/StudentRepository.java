package com.csvdatanatwest.natwestcsvdata.repositories;

import com.csvdatanatwest.natwestcsvdata.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
