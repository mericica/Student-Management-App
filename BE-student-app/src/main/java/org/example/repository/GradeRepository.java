package org.example.repository;

import org.example.entity.Grade;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    //List<Grade> findGradeByUser(int userId);
}
