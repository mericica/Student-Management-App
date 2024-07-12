package org.example.controller;

import org.example.entity.CourseEntity;
import org.example.entity.Grade;
import org.example.entity.UserEntity;
import org.example.exception.CnpValidator;
import org.example.exception.ErrorCode;
import org.example.exception.InvalidCnpException;
import org.example.model.UserRequest;
import org.example.model.UserResponse;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponse add(@RequestBody UserRequest requestDto) {
        if (!CnpValidator.validateCnp(requestDto.getCnp())) {
            throw new InvalidCnpException(ErrorCode.INVALIDCNP);
        }
        /*
        if (!PasswordValidator.validatePassword(requestDto.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALIDPASSWORD);
        }

         */
        return userService.addUser(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UserRequest requestDto) {
        if (!CnpValidator.validateCnp(requestDto.getCnp())) {
            throw new InvalidCnpException(ErrorCode.INVALIDCNP);
        }
        /*
        if (!PasswordValidator.validatePassword(requestDto.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALIDPASSWORD);
        }

         */
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok("User successfully updated");
    }

    @PostMapping("/{userEmail}/{roleid}")
    public ResponseEntity<String> assignRolesToUser(@PathVariable String userEmail, @PathVariable Long roleid) {
        userService.assignRoleToUser(userEmail, roleid);
        return ResponseEntity.ok("Roles assigned successfully");
    }

    @GetMapping(path = "/{id}")
    public UserResponse get(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id) {
        return userService.deleteUser(id);
    }


    @GetMapping()
    public List<UserResponse> getUsers(@RequestParam(value="name") String name) {
        return userService.filter(name);
    }


    @GetMapping("/")
    public List<UserEntity> getUsers() {
        return userService.getAll();
    }

    @PutMapping("/assignGrade")
    public void addGrade(@RequestBody Grade grade) {
        userService.addGrade(grade);
    }

    @DeleteMapping("/deleteGrade/{userId}/{courseId}")
    public void deleteGrade(@PathVariable long userId, @PathVariable long courseId) {
        userService.deleteGrade(userId, courseId);
    }

    @GetMapping("/courses")
    public List<CourseEntity> getCourses() {
        return userService.getAllCourses();
    }
}