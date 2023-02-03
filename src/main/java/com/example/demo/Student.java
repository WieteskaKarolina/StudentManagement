package com.example.demo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String indexNumber;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudentSubject> subjects = new ArrayList<>();

    public void addSubject(Subject subject, Integer grade) {
        StudentSubject studentSubject = new StudentSubject(this, subject, grade);
        subjects.add(studentSubject);
        subject.getStudents().add(studentSubject);
    }

    public void removeSubject(Long subjectId) {
        for (Iterator<StudentSubject> iterator = subjects.iterator();
             iterator.hasNext(); ) {
            StudentSubject studentSubject = iterator.next();
            if (studentSubject.getStudent().equals(this) &&
                    studentSubject.getSubject().getId().equals(subjectId)) {
                iterator.remove();
                studentSubject.getSubject().getStudents().remove(studentSubject);
                studentSubject.setSubject(null);
                studentSubject.setStudent(null);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return id != null && id.equals(((Student) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}