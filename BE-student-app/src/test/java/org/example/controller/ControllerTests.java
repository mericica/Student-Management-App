package org.example.controller;
/*

import org.example.exception.ErrorCode;
import org.example.exception.IdDoesntExistException;
import org.example.exception.InvalidPasswordException;
import org.example.mapper.UserMapper;
import org.example.model.*;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

public class ControllerTests {
    private UserService service;
    private UserController controller;

    @BeforeEach
    public void setUp() {
        service = Mockito.mock(UserService.class);
        controller = new UserController(service);
    }
    @Test
    public void givenValidInput_whenAddStudent_thenCreateStudent() throws Exception {
        //given
        String cnp = "1234567123456";
        String firstName = "a";
        String lastName = "b";
        String email = "mail";
        String password = "Pass1";
        UserRequest expectedUser = new UserRequest(0L, cnp, firstName, lastName, email, password);
        when(service.addUser(any(UserRequest.class))).thenReturn(UserMapper.userRequestDtoToUserResponseDto(expectedUser));
        // when
        ResponseEntity<?> actualUser = controller.add(expectedUser);

        // then
        assertThat(actualUser.getStatusCode()).isEqualTo(OK);
        assertThat(actualUser.getBody()).isEqualTo("User successfully added");
        verify(service).addUser(expectedUser);
        verifyNoMoreInteractions(service);
    }
    @Test
    public void givenValidId_whenGetStudent_thenReturnStudent() {
        String cnp = "1234567123456";
        String firstName = "a";
        String lastName = "b";
        String email = "mail";
        String password = "Pass1";
        UserResponse expectedUser = new UserResponse(0L, cnp, firstName, lastName, email, password);
        when(service.getUser(any(Long.class))).thenReturn(expectedUser);

        // when
        UserResponse actualStudent = controller.get(1);

        // then
        assertThat(actualStudent).isEqualTo(expectedUser);
        verify(service).getUser(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void givenNonExistentId_whenGetStudent_thenThrowException() {
        //given
        when(service.getUser(any(Long.class))).thenThrow(IdDoesntExistException.class);

        //when
        assertThrows(IdDoesntExistException.class, () -> {
            controller.get(999L);
        });

        //then
        verify(service).getUser(999L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void givenValidId_whenDeleteStudent_thenStudentDeleted() {
        //given
        when(service.deleteUser(any(Long.class))).thenReturn(true);

        // when
        boolean actualOutcome = controller.delete(1L);

        // then
        assertThat(actualOutcome).isEqualTo(true);
        verify(service).deleteUser(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void givenNonExistentId_whenDeleteStudent_thenThrowException() {
        //given
        when(service.deleteUser(any(Long.class))).thenThrow(new IdDoesntExistException(ErrorCode.INVALIDID));

        // when
        Exception exception = assertThrows(Exception.class, () -> {
            controller.delete(999);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo("No User with this ID was found");
        verify(service).deleteUser(999L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void givenDataToUpdate_whenUpdateStudent_thenReturnUpdatedStudent() {
        //given
        long id = 1;
        String updatedCnp = "1234559029222";
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        String email = "mail";
        String password = "Pass1";
        UserRequest expectedUser = new UserRequest(id, updatedCnp, updatedFirstName, updatedLastName, email, password);
        when(service.updateUser(any(Long.class), any(UserRequest.class))).thenReturn(UserMapper.userRequestDtoToUserResponseDto(expectedUser));

        //when
        ResponseEntity<?> actualUser = controller.updateUser(id, expectedUser);

        //then
        assertThat(actualUser.getStatusCode()).isEqualTo(OK);
        assertThat(actualUser.getBody()).isEqualTo("User successfully updated");
        verify(service).updateUser(id, expectedUser);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void givenNonExistentStudentId_whenUpdateStudent_thenThrowIDDoesntExistException() {
        // given
        String wrongPass = "a";

        //when
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> controller.updateUser(1, new UserRequest(1L, "1234567890123", "f", "f", "mail", wrongPass)));

        // then
        assertThat("Password should start with an uppercase letter, followed by lowercase letters and at least a number").isEqualTo(exception.getMessage());
    }

    @Test
    void givenStudentDetails_whenFilterStudents_thenReturnFilteredStudents() {
        //given
        UserResponse s1 = new UserResponse(1L, "12345", "Bob", "Doe", "a", "Pass3");
        UserResponse s2 = new UserResponse(2L, "67890", "Lob", "Bob", "v", "Pass4");
        List<UserResponse> expectedStudentList = List.of(s1, s2);
        when(service.filter(anyString())).thenReturn(expectedStudentList);

        //when
        List<UserResponse> actualStudentList = controller.getUsers("Bob");

        //then
        assertThat(actualStudentList).isEqualTo(expectedStudentList);
        verify(service).filter(eq("Bob"));
        verifyNoMoreInteractions(service);
    }

    @Test
    void givenStudentDetails_whenGetStudents_thenReturnAllStudents() {
        //given
        UserRequest s1 = new UserRequest(1L, "1234567890124", "Maria", "M", "v", "Pass4");
        UserRequest s2 = new UserRequest(2L, "1234567890123", "Maria", "M", "v", "Pass4");
        List<UserRequest> expectedStudentRequest = List.of(s1, s2);
        List<UserResponse> expectedStudentResponseDto = expectedStudentRequest.stream()
                .map(UserMapper::userRequestDtoToUserResponseDto)
                .toList();
        when(service.getAll()).thenReturn(expectedStudentResponseDto);

        //when
        List<UserResponse> actualStudentDtoList = controller.getUsers(null);

        //then
        assertThat(actualStudentDtoList).usingElementComparatorOnFields("id", "cnp", "firstName", "lastName", "email", "password").containsExactlyInAnyOrderElementsOf(expectedStudentResponseDto);
        verify(service).getAll();
        verifyNoMoreInteractions(service);
        assertThat(controller.getUsers(null)).hasSize(2);
    }
}

 */