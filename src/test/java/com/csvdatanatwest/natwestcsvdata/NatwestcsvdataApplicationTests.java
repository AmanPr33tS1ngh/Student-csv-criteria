package com.csvdatanatwest.natwestcsvdata;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.csvdatanatwest.natwestcsvdata.models.Student;
import com.csvdatanatwest.natwestcsvdata.models.Criteria;
import com.csvdatanatwest.natwestcsvdata.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class NatwestcsvdataApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StudentService studentService;

    @Test
    void testUploadCsv() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.csv", "text/csv",
                "roll_number,student_name,science,maths,english,computer,Eligible\n100101,Vivek Sharma,86,89,78,92,ToBeChecked".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload_csv").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStudent() {
        Optional<Student> student = studentService.getStudent(100101L);
        assertEquals("Vivek Sharma", student.get().getName());
    }

    @Test
    void testUpdateCriteria() {
        // studentService.createOrUpdateCriteria(new Criteria(45, 55, 44, 55));
        // assertEquals(90, studentService.getCriteria().getScience());
    }
}
