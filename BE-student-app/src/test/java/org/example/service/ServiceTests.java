package org.example.service;
/*
import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.exception.CnpAlreadyInUseException;
import org.example.exception.IdDoesntExistException;
import org.example.mapper.UserMapper;
import org.example.model.UserRequest;
import org.example.model.UserResponse;
import org.example.repository.RoleDbRepository;
import org.example.repository.UserDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ServiceTests {

    private UserDBRepository userRepo;
    private RoleDbRepository roleuserRepo;
    private UserService service;

    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        userRepo = mock(UserDBRepository.class);
        roleuserRepo = mock(RoleDbRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        service = new UserDbServiceImpl(userRepo, roleuserRepo, authenticationManager) {
        };
    }

    @Test
    public void givenStudentDetails_whenAddStudent_thenReturnNewStudent() {
        // given
        String cnp = "7234567891012";
        String firstName = "a";
        String lastName = "b";
        String email = "email";
        String password = "Pass1";
        UserRequest userRequest = new UserRequest(0L, cnp, firstName, lastName, email, password);
        UserEntity user = UserMapper.userRequestToUserEntity(userRequest);
        when(userRepo.myFindAll()).thenReturn(Collections.emptyList());
        when(userRepo.save(any(UserEntity.class))).thenReturn(user);
        when(userRepo.findByCnp(any(String.class))).thenReturn(Optional.empty());

        // when
        UserResponse actualStudentDto = service.addUser(userRequest);

        // then
        assertThat(actualStudentDto).isEqualTo(UserMapper.userRequestDtoToUserResponseDto(userRequest));
        verify(userRepo).findByCnp(cnp);
        verify(userRepo).save(user);
        verify(userRepo).myFindById(eq(0L));
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void givenDuplicateCNP_whenAddStudent_thenThrowException() {
        // given
        String cnp = "7234567891012";
        String email = "email";
        String password = "Pass1";
        UserRequest existingUser = new UserRequest(1L, cnp, "Existing", "Student", email, password);
        UserEntity user = UserMapper.userRequestToUserEntity(existingUser);
        doNothing().when(userRepo).save(any(UserEntity.class));
        when(userRepo.myFindAll()).thenReturn(Collections.singletonList(user));

        // when
        Exception exception = assertThrows(CnpAlreadyInUseException.class, () -> service.addUser(existingUser));

        // then
        assertThat(exception.getMessage()).isEqualTo("Another User already exists with that cnp");
        verify(userRepo, never()).save(user);
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenStudentDetails_whenGetStudent_thenReturnStudent() {
        //given
        String cnp = "7034567891012";
        String firstName = "a";
        String lastName = "b";
        String email = "email";
        String password = "Pass1";
        UserRequest expectedUserDto = new UserRequest(2L, cnp, firstName, lastName, email, password);
        when(userRepo.myFindById(any(Long.class))).thenReturn(Optional.of(UserMapper.userRequestToUserEntity(expectedUserDto)));
        when(userRepo.myFindAll()).thenReturn(Collections.singletonList(UserMapper.userRequestToUserEntity(expectedUserDto)));

        //when
        UserResponse actualStudentDto = service.getUser(2L);

        //then
        assertThat(actualStudentDto).isEqualTo(UserMapper.userEntityToUserResponseDto(UserMapper.userRequestToUserEntity(expectedUserDto)));
        verify(userRepo).myFindById(eq(2L));
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void givenNonExistentStudentId_whenGetStudent_thenThrowNotFoundException() {
        // given
        Long nonExistentStudentId = 999L;
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        when(userRepo.myFindAll()).thenReturn((Collections.emptyList()));

        //when
        IdDoesntExistException exception = assertThrows(IdDoesntExistException.class, () -> service.getUser(nonExistentStudentId));

        //then
        assertThat(exception.getMessage()).isEqualTo("No User with this ID was found");
        verify(userRepo, never()).findById(any(Long.class));
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenStudentDetails_whenDeleteStudent_thenDeleteStudent() {
        //given
        String cnp = "1234559029222";
        String firstName = "a";
        String lastName = "b";
        String email = "email";
        String password = "Pass1";
        UserEntity expectedStudent = new UserEntity(0L, cnp, firstName, lastName, email, password);
        doNothing().when(userRepo).myDelete(any(Long.class));
        when(userRepo.myFindById(any(Long.class))).thenReturn(Optional.of(expectedStudent));
        when(userRepo.myFindAll()).thenReturn(Collections.singletonList(expectedStudent));

        //when
        boolean actualStudent = service.deleteUser(0L);

        //then
        assertThat(actualStudent).isEqualTo(true);
        verify(userRepo).myFindById(0L);
        verify(userRepo).myDelete(eq(0L));
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    public void givenNonExistentStudentId_whenDeleteStudent_thenThrowNotFoundException() {
        // given
        Long nonExistentStudentId = 999L;
        doNothing().when(userRepo).myDelete(any(Long.class));
        when(userRepo.myFindAll()).thenReturn((Collections.emptyList()));

        //when
        IdDoesntExistException exception = assertThrows(IdDoesntExistException.class, () -> service.deleteUser(nonExistentStudentId));

        //then
        assertThat(exception.getMessage()).isEqualTo("No User with this ID was found");
        verify(userRepo, never()).myDelete(999L);
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenStudentDetails_whenUpdateStudent_returnUpdatedStudent() {
        //given
        Long studentId = 1L;
        String updatedCnp = "1234559029222";
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        String email = "email";
        String password = "Pass1";
        UserEntity existingStudent = new UserEntity(studentId, "1234567890123", "FirstName", "LastName", email, password);
        UserRequest updatedStudent = new UserRequest(studentId, updatedCnp, updatedFirstName, updatedLastName, email, password);
        doNothing().when(userRepo).myUpdate(any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
        when(userRepo.myFindAll()).thenReturn(Collections.singletonList(existingStudent));
        when(userRepo.myFindById(any(Long.class))).thenReturn(Optional.of(UserMapper.userRequestToUserEntity(updatedStudent)));

        //when
        UserResponse actualStudent = service.updateUser(studentId, updatedStudent);

        //then
        assertThat(actualStudent).isEqualTo(UserMapper.userRequestDtoToUserResponseDto(updatedStudent));
        verify(userRepo, times(2)).myUpdate(1L, updatedCnp, updatedFirstName, updatedLastName, email, password);
        verify(userRepo, times(2)).myFindAll();
        verify(userRepo).myFindById(1L);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenNonExistingID_whenUpdateStudent_thenThrowException() {
        // given
        Long nonExistentStudentId = 999L;
        doNothing().when(userRepo).myUpdate(any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
        when(userRepo.myFindAll()).thenReturn(Collections.emptyList());

        // when
        IdDoesntExistException exception = assertThrows(IdDoesntExistException.class, () -> service.updateUser(nonExistentStudentId, new UserRequest(nonExistentStudentId, "123456789123", "f", "f", "email", "pass")));

        // then
        assertThat("No User with this ID was found").isEqualTo(exception.getMessage());
        verify(userRepo, never()).myUpdate(nonExistentStudentId, "123456789123", "f", "f", "email", "pass");
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenStudentDetails_whenFilterStudents_returnFilteredStudents() {
        //given
        UserEntity s1 = new UserEntity(1L, "12345", "Bob", "Doe", "email", "Pass3");
        UserEntity s2 = new UserEntity(2L, "67890", "Lob", "Bob", "email", "Pass2");
        List<UserEntity> expectedStudentList = List.of(s1, s2);
        List<UserResponse> expectedStudentListDto = expectedStudentList.stream()
                .map(UserMapper::userEntityToUserResponseDto)
                .toList();
        when(userRepo.findUserEntitiesByFirstNameOrLastName(anyString(), anyString())).thenReturn(expectedStudentList);

        //when
        List<UserResponse> actualStudentList = service.filter("Bob");

        //then
        assertThat(actualStudentList).usingElementComparatorOnFields("id", "cnp", "firstName", "lastName").containsExactlyInAnyOrderElementsOf(expectedStudentListDto);
        verify(userRepo).findUserEntitiesByFirstNameOrLastName("Bob", "Bob");
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void givenStudents_whenGetStudents_thenFindAllStudents() {
        //given
        UserRequest s1 = new UserRequest(1L, "1234567890124", "Maria", "M", "mail1", "Pass1");
        UserRequest s2 = new UserRequest(2L, "1234567890123", "Maria", "M", "mail2", "pass2");
        List<UserRequest> expectedStudentRequestDto = List.of(s1, s2);
        List<UserResponse> expectedStudentResponseDto = expectedStudentRequestDto.stream()
                .map(UserMapper::userRequestDtoToUserResponseDto)
                .toList();
        when(userRepo.myFindAll()).thenReturn(List.of(UserMapper.userRequestToUserEntity(s1), UserMapper.userRequestToUserEntity(s2)));

        //when
        List<UserResponse> actualStudentDtoList = service.getAll();

        //then
        assertThat(actualStudentDtoList).isEqualTo(expectedStudentResponseDto);
        verify(userRepo).myFindAll();
        verifyNoMoreInteractions(userRepo);
        assertThat(service.getAll()).hasSize(2);
    }

    @Test
    public void givenUserIdAndRoleId_whenAssignRoleToUser_thenAssignRoleToUser() {
        // given
        String email = "aa";
        Long roleId = 1L;
        RoleEntity role = new RoleEntity(1L, "admin");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setRoles(new ArrayList<>());
        when(roleuserRepo.findAll()).thenReturn(Collections.singletonList(role));
        when(roleuserRepo.findById(any(Long.class))).thenReturn(Optional.of(role));
        when(userRepo.myFindById(any(Long.class))).thenReturn(Optional.of(userEntity));

        // when
        service.assignRoleToUser(email, roleId);

        // then
        verify(roleuserRepo).findAll();
        verify(userRepo).findByEmail(email);
        verify(roleuserRepo).findById(roleId);
        verify(userRepo).save(userEntity);
        verifyNoMoreInteractions(userRepo);
    }
}

 */