package org.example.repository;

import org.example.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDbRepository extends JpaRepository<RoleEntity, Long> {
    @Query(value = "select *  from role where id = :id", nativeQuery = true)
    Optional<RoleEntity> myFindById(@Param("id") Long id);

    @Query(value = "select id from role where name = :name", nativeQuery = true)
    Long findIdByRoleName(@Param("name") String name);
}