package com.example.demo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentSubject {

    @EmbeddedId
    private StudentSubjectId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;
    @ManyToOne
    @MapsId("subjectId")
    private Subject subject;
    private Integer grade;

    public StudentSubject(Student student, Subject subject, Integer grade) {
        this.student = student;
        this.subject = subject;
        this.id = new StudentSubjectId(student.getId(), subject.getId());
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentSubject that = (StudentSubject) o;
        return Objects.equals(student, that.student) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject);
    }
}
