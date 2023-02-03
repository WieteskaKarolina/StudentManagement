package com.example.demo;

import lombok.Data;

@Data
public class StudentSubjectForm {
    private Student student;
    private String subjectName;
    private Integer grade;
}
