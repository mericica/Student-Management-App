package org.example.repository;

import org.example.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserDBRepositoryTest {

    @Autowired
    private UserDBRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    public void setUp() {
        testUser = new UserEntity();
        testUser.setCnp("1234567890987");
        testUser.setPassword("Pass1");
        testUser.setEmail("email");
        testUser.setFirstName("First");
        testUser.setLastName("Last");
        userRepository.save(testUser);
    }

    @AfterEach
    public void tearDown() {
        userRepository.myDelete(1L);
    }

    @Test
    public void givenUserData_whenSaveStudent_thenSaveStudent() {
        Optional<UserEntity> savedUser = userRepository.myFindById(1L);
        UserEntity entity = savedUser.get();
        assertThat(entity.getCnp()).isEqualTo(testUser.getCnp());
        assertThat(entity.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(entity.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(entity.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(entity.getPassword()).isEqualTo(testUser.getPassword());
    }

    @Test
    public void givenDataInDB_whenFindAll_thenListNotEmpty() {
        assertThat(userRepository.myFindAll()).isNotEmpty();
    }

    @Test
    public void givenUserData_whenUpdateUser_thenUserUpdated() {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setCnp("1234567890123");
        updatedUser.setFirstName("UpdatedFirst");
        updatedUser.setLastName("UpdatedLast");
        updatedUser.setEmail("updated@email.com");
        updatedUser.setPassword("UpdatedPass");

        userRepository.myUpdate(1L, "1234567890123", "UpdatedFirst", "UpdatedLast", "updated@email.com", "UpdatedPass");

        Optional<UserEntity> retrievedUserOptional = userRepository.myFindById(1L);
        UserEntity retrievedUser = retrievedUserOptional.get();
        assertThat(retrievedUser.getCnp()).isEqualTo(updatedUser.getCnp());
        assertThat(retrievedUser.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(retrievedUser.getLastName()).isEqualTo(updatedUser.getLastName());
        assertThat(retrievedUser.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(retrievedUser.getPassword()).isEqualTo(updatedUser.getPassword());
    }

    @Test
    public void givenId_whenFindUserById_thenReturnUser() {
        Optional<UserEntity> foundUser = userRepository.myFindById(1L);
        assertThat(foundUser.get().getId()).isEqualTo(1L);
    }

    @Test
    public void givenFirstNameAndLastName_whenFindUserEntitiesByFirstNameOrLastName_thenUserListReturned() {
        List<UserEntity> userList = userRepository.findUserEntitiesByFirstNameOrLastName("First", "Last");
        assertThat(userList).isNotEmpty();
        assertThat(userList.get(0).getFirstName()).isEqualTo("First");
        assertThat(userList.get(0).getLastName()).isEqualTo("Last");
    }

    @Test
    public void givenId_whenDeleteUser_thenUserDeleted() {
        userRepository.myDelete(1L);
        Optional<UserEntity> deletedUser = userRepository.myFindById(1L);
        assertThat(deletedUser.isPresent()).isFalse();
    }
}