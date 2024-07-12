package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.UserEntity;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {
    @Id
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    List<UserEntity> users;

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}