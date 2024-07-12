package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDBRepository extends JpaRepository<UserEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "update user_entity set cnp = :cnp , first_name = :first_name , last_name = :last_name , email = :email , password = :password " +
            "where id = :id", nativeQuery = true)
    void myUpdate(@Param("id") Long id, @Param("cnp") String cnp, @Param("first_name") String first_name, @Param("last_name") String last_name,
                @Param("email") String email, @Param("password") String password);

    @Query(value = "select *  from user_entity where id = :id", nativeQuery = true)
    Optional<UserEntity> myFindById(@Param("id") Long id);

    @Query(value = "select * from user_entity", nativeQuery = true)
    List<UserEntity> myFindAll();

    @Transactional
    @Modifying
    @Query(value = "delete from user_entity where id = :id", nativeQuery = true)
    void myDelete(@Param("id") Long id);

    List<UserEntity> findUserEntitiesByFirstNameOrLastName(String name, String name2);

    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findByCnp(String cnp);
}