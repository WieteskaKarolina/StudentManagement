package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    private List<StudentSubject> students = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        return id != null && id.equals(((Subject) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}