package com.csvdatanatwest.natwestcsvdata.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class CsvProcessingService {
    private final StudentService studentService;

    @Autowired
    private CriteriaService criteriaService;


    public List<Student> processCsv(List<Student> students) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<List<Student>>> futures = new ArrayList<>();
        List<Student> processedStudents = new ArrayList<>();

        int chunkSize = students.size() / 4;

        for (int i = 0; i < 4; i++) {
            int startIdx = i * chunkSize;
            int endIdx = (i == 3) ? students.size() : (i + 1) * chunkSize;
            List<Student> chunk = students.subList(startIdx, endIdx);

            Future<List<Student>> future = executorService.submit(new Callable<List<Student>>() {
                @Override
                public List<Student> call() throws Exception {
                    return processChunk(chunk);
                }
            });

            futures.add(future);
        }

        // Collect the results
        for (Future<List<Student>> future : futures) {
            try {
                List<Student> processedChunk = future.get();
                processedStudents.addAll(processedChunk);
            } catch (Exception e) {}
        }

        // Shut down the executor service
        executorService.shutdown();

        return processedStudents;
    }
    
    private List<Student> processChunk(List<Student> chunk) {
        Criteria criteria = criteriaService.getCriteria();
        if(criteria == null){ // Criteria not found. Creating one with default values that are provided in mail.
            criteria = new Criteria(90, 85, 75, 95);
            criteriaService.createOrUpdateCriteria(criteria);
        }

        for (Student student : chunk) {
            boolean eligible = student.getScience() > criteria.getScience()
                    && student.getMaths() > criteria.getMaths()
                    && student.getEnglish() > criteria.getEnglish()
                    && student.getComputer() > criteria.getComputer();

            student.setEligibility(eligible ? "YES" : "NO");

            try {
                studentService.saveStudents(chunk);
            } catch (Exception e) {}
        }
        return chunk;
    }
}
