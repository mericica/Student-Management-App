package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int semester;

    public CourseEntity(Long id, String name, int semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Grade> grades = new ArrayList<>();
}
