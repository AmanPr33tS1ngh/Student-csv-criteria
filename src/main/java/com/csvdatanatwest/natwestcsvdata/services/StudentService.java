package com.csvdatanatwest.natwestcsvdata.services;

import com.csvdatanatwest.natwestcsvdata.models.Criteria;
import com.csvdatanatwest.natwestcsvdata.models.Student;
import com.csvdatanatwest.natwestcsvdata.repositories.StudentRepository;
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
