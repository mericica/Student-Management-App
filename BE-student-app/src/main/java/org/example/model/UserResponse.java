package org.example.model;

import lombok.Data;
import org.example.entity.Grade;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cnp;

    public UserResponse(Long id,String cnp, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
