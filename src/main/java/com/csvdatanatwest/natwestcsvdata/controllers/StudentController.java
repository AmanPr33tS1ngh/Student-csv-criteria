package com.csvdatanatwest.natwestcsvdata.controllers;

import lombok.RequiredArgsConstructor;

import com.csvdatanatwest.natwestcsvdata.models.Student;
import com.csvdatanatwest.natwestcsvdata.services.CsvProcessingService;
import com.csvdatanatwest.natwestcsvdata.services.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Tag(name = "Student APIs", description = "APIs for Student Data")
public class StudentController {
    private final StudentService studentService;
    private final CsvProcessingService csvProcessingService;

    @PostMapping("/upload_csv")
    @Operation(summary = "Upload Student CSV", description = "Returns updated csv. Note: Before this method please create/update criteria using criteria endpoint.")
    public ResponseEntity<byte[]> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(400).body(null);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Student> students = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] fields = line.split(",");
                        return new Student(
                                Long.parseLong(fields[0]),
                                fields[1],
                                Integer.parseInt(fields[2]),
                                Integer.parseInt(fields[3]),
                                Integer.parseInt(fields[4]),
                                Integer.parseInt(fields[5]),
                                "ToBeChecked"
                        );
                    })
                    .collect(Collectors.toList());
            List<Student> updatedStudents = csvProcessingService.processCsv(students);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out))) {
                writer.println("roll_number,student_name,science,maths,english,computer,eligibility");
                for (Student student : updatedStudents) {
                    writer.println(String.format("%d,%s,%d,%d,%d,%d,%s",
                            student.getRollNumber(), student.getName(), student.getScience(), student.getMaths(),
                            student.getEnglish(), student.getComputer(), student.getEligibility()));
                }
            }

            // Returning new CSV as byte array
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=updated_students.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
            return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{rollNumber}")
    @Operation(summary = "Upload Student CSV", description = "Returns updated csv. Note: Before this method please create/update criteria using criteria endpoint.")
    public ResponseEntity<Student> getStudent(@PathVariable Long rollNumber) {
        Optional<Student> student = studentService.getStudent(rollNumber);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }
}
