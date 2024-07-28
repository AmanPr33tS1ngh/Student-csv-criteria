package com.csvdatanatwest.natwestcsvdata.controller;

import lombok.RequiredArgsConstructor;

import com.csvdatanatwest.natwestcsvdata.model.Student;
import com.csvdatanatwest.natwestcsvdata.service.CsvProcessingService;
import com.csvdatanatwest.natwestcsvdata.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

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

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping(value = "/upload_csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Student CSV", description = "Returns updated csv")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully uploaded and processed CSV",
                content = @Content(schema = @Schema(type = "string", format = "binary"))),
        @ApiResponse(responseCode = "400", description = "File is empty or invalid", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<byte[]> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("File is empty or wrong file type");
            return ResponseEntity.status(400).body(null);
        }

        logger.info("Reading CSV and creating Student objects");
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

            logger.info("Processing Student objects");
            List<Student> updatedStudents = csvProcessingService.processCsv(students);

            logger.info("Writing Updated Students");
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
    @Operation(summary = "Upload Student CSV", description = "Returns Student with the same roll no.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Got Student"),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
    })
    public ResponseEntity<Student> getStudent(@PathVariable Long rollNumber) {
        Optional<Student> student = studentService.getStudent(rollNumber);
        if (student == null){
            logger.error("Student not found");
            return ResponseEntity.status(404).body(null);
        }
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }
}
