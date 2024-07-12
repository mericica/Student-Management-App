package org.example.service;

import org.example.entity.CourseEntity;
import org.example.entity.Grade;
import org.example.entity.UserEntity;
import org.example.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserResponse addUser(UserRequest userRequest);

    UserResponse getUser(Long id);

    boolean deleteUser(Long id);

    UserResponse updateUser(Long id, UserRequest userRequest);

    List<UserResponse> filter(String name);

    List<UserEntity> getAll();

    List<CourseEntity> getAllCourses();
    void deleteGrade(long userId, long gradeId);

    void assignRoleToUser(String userEmail, Long roles);

    void signUpCheck(SignUpRequest signUpRequest) throws Exception;

    boolean addGrade(Grade grade);

    public LoginResponse login(LoginRequest loginRequest);
    public ResponseEntity<String> signUp( SignUpRequest signUpRequest) throws Exception;
}