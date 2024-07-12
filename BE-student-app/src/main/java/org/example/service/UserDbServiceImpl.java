package org.example.service;

import jakarta.transaction.Transactional;
import org.example.authentification.JwtUtil;
import org.example.entity.CourseEntity;
import org.example.entity.Grade;
import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.exception.*;
import org.example.mapper.UserMapper;
import org.example.model.*;
import org.example.repository.CourseRepository;
import org.example.repository.GradeRepository;
import org.example.repository.RoleDbRepository;
import org.example.repository.UserDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDbServiceImpl implements UserService {

    private final UserDBRepository userDBRepository;
    private final RoleDbRepository roleDBRepository;

    private final GradeRepository gradeRepository;

    private final CourseRepository courseRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;


    public UserDbServiceImpl(UserDBRepository userDBRepository, RoleDbRepository roleDBRepository, AuthenticationManager authenticationManager, GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.userDBRepository = userDBRepository;
        this.roleDBRepository = roleDBRepository;
        this.authenticationManager = authenticationManager;
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
    }

    private void existsByCnp(UserRequest userRequest) {
        if (userDBRepository.findByCnp(userRequest.getCnp()).isPresent()) {
            throw new CnpAlreadyInUseException(ErrorCode.CNPALREADYINUSE);
        }
    }

    private void existsByCnpForUpdate(UserRequest userRequest) {
        Optional<UserEntity> user = userDBRepository.findByCnp(userRequest.getCnp());
        if (user.isPresent() && !Objects.equals(user.get().getId(), userRequest.getId())) {
            throw new CnpAlreadyInUseException(ErrorCode.CNPALREADYINUSE);
        }
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        existsByCnp(userRequest);
        UserEntity newUser = UserMapper.userRequestToUserEntity(userRequest);
        return UserMapper.userEntityToUserResponseDto(userDBRepository.save(newUser));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        if (userDBRepository.myFindById(id).isEmpty()) {
            throw new IdDoesntExistException(ErrorCode.INVALIDID);
        }
        existsByCnpForUpdate(userRequest);
        UserEntity newUser = UserMapper.userRequestToUserEntity(userRequest);
        userDBRepository.myUpdate(id, newUser.getCnp(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword());
        Optional<UserEntity> returnedUserOptional = userDBRepository.myFindById(userRequest.getId());
        return returnedUserOptional.map(UserMapper::userEntityToUserResponseDto).orElse(null);
    }

    @Override
    public void assignRoleToUser(String userEmail, Long roleid) {
        if (roleDBRepository.findById(roleid).isEmpty()) {
            throw new RoleIdDoesntExistException(ErrorCode.INVALIDROLE);
        }
        UserEntity user = userDBRepository.findByEmail(userEmail).get();
        List<RoleEntity> userRoles = user.getRoles();
        RoleEntity searchedRole = roleDBRepository.findById(roleid).get();

        if (userRoles.stream().anyMatch(roleEntity -> Objects.equals(roleEntity.getId(), roleid))) {
            throw new DuplicateRoleUserCombinationException(ErrorCode.DUPLICATEROLE);
        }
        userRoles.add(searchedRole);
        userDBRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long id) {
        if (userDBRepository.myFindById(id).isEmpty()) {
            throw new IdDoesntExistException(ErrorCode.INVALIDID);
        }
        Optional<UserEntity> returnedUserOptional = userDBRepository.myFindById(id);
        return returnedUserOptional.map(UserMapper::userEntityToUserResponseDto).orElse(null);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<UserEntity> user = userDBRepository.myFindById(id);
        if (user.isPresent()) {

             gradeRepository.findAll().stream()
                    .filter(grade -> Objects.equals(grade.getUser().getId(), user.get().getId()))
                    .forEach(gradeRepository::delete);

            userDBRepository.myDelete(id);
            System.out.println("> User successfully deleted");
            return true;
        }
        throw new IdDoesntExistException(ErrorCode.INVALIDID);
    }

    @Override
    public List<UserResponse> filter(String name) {
        return userDBRepository.findUserEntitiesByFirstNameOrLastName(name, name).stream().map(UserMapper::userEntityToUserResponseDto).toList();
    }

    @Override
    public List<UserEntity> getAll() {
        return userDBRepository.myFindAll();
        /*
        return userList.stream()
                .map(UserMapper::userEntityToUserResponseDto)
                .collect(Collectors.toList());

         */
    }

    @Override
    public void signUpCheck(SignUpRequest signUpRequest) throws Exception {
        if (userDBRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String email = authentication.getName();
        UserEntity user = new UserEntity(email, "");
        String token = jwtUtil.createToken(user);
        return new LoginResponse(email, token);
    }

    @Override
    public ResponseEntity<String> signUp(SignUpRequest signUpRequest) throws Exception {
        signUpCheck(signUpRequest);
        UserRequest userRequest = new UserRequest(0L, signUpRequest.getCnp(), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        addUser(userRequest);
        assignRoleToUser(signUpRequest.getEmail(), 1L);
        return ResponseEntity.ok("User successfully registered");
    }

    @Transactional
    @Override
    public boolean addGrade(Grade grade) {
        Optional<UserEntity> founduser = userDBRepository.findById(grade.getUser().getId());
        if (founduser.isEmpty()) {
            return false;
        }
        if(!grade.getGrade().matches("^[1-9]|10$"))
            throw new RuntimeException("Not valid grade number");
        System.out.println(grade.getUser().getId());
        Optional<Grade> availableGrade = gradeRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(grade.getUser().getId()) && x.getCourse().getId().equals(grade.getCourse().getId())).findAny();
        if (availableGrade.isPresent()) {
           return false;
        }
        List<Grade> userGrades = founduser.get().getGrades();
        userGrades.add(grade);
        founduser.get().setGrades(userGrades);
        gradeRepository.save(grade);
        return true;
    }

    @Override
    public void deleteGrade(long userId, long courseId) {
        Optional<Grade> grade = gradeRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(userId) && x.getCourse().getId().equals(courseId)).findAny();
        if (grade.isEmpty()) {
            throw new RuntimeException("grade not found");
        }
        gradeRepository.delete(grade.get());
    }

    @Override
    public List<CourseEntity> getAllCourses(){
        return courseRepository.findAll();
    }
}