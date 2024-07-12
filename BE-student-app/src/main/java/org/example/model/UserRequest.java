package org.example.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cnp;

    public UserRequest(Long id, String cnp, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRequest that = (UserRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(cnp, that.cnp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, cnp);
    }
}