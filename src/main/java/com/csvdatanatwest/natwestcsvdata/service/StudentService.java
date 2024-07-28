package com.csvdatanatwest.natwestcsvdata.service;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.model.Student;
import com.csvdatanatwest.natwestcsvdata.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private Criteria criteria = new Criteria();

    @Transactional
    public void saveStudents(List<Student> students) {
        studentRepository.saveAll(students);
    }

    public Optional<Student> getStudent(Long rollNumber) {
        return studentRepository.findById(rollNumber);
    }

    public void updateCriteria(Criteria newCriteria) {
        this.criteria = newCriteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }
}
