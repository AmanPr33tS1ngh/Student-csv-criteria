package com.csvdatanatwest.natwestcsvdata.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.model.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CsvProcessingService {
    private final StudentService studentService;

    @Autowired
    private CriteriaService criteriaService;

    public List<Student> processCsv(List<Student> students) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int chunkSize = students.size() / 4;
        
        for (int i = 0; i < 4; i++) {
            int startIdx = i * chunkSize;
            int endIdx = (i == 3) ? students.size() : (i + 1) * chunkSize;
            List<Student> chunk = students.subList(startIdx, endIdx);
            
            executorService.submit(() -> {
                try {
                    processChunk(chunk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        return students;
    }

    private void processChunk(List<Student> chunk) {
        Criteria criteria = criteriaService.getCriteria();
        for (Student student : chunk) {
            boolean eligible = student.getScience() > criteria.getScience()
                    && student.getMaths() > criteria.getMaths()
                    && student.getEnglish() > criteria.getEnglish()
                    && student.getComputer() > criteria.getComputer();

            student.setEligibility(eligible ? "YES" : "NO");
            
            try {
                studentService.saveStudents(chunk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
