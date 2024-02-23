package com.findar.auth.dto;


import com.findar.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateUserRequest {
    @NotBlank(message = "Is required")
    private String firstName;

    @NotBlank(message = "Is required")
    private String lastName;

    @NotBlank(message = "Is required")
    @Email(message = "invalid format")
    private String username;

    @NotBlank(message = "Is required")
    private String password;

    public User toEntity() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}