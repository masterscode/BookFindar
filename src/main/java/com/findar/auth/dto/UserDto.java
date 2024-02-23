package com.findar.auth.dto;


import com.findar.user.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;


@Data
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private Long id;

    private boolean deleted;


    private Collection<String> permissions;

    protected LocalDateTime dateCreated;

    public UserDto(User user) {
        id = user.getId();
        dateCreated = user.getDateCreated();
        deleted = user.isDeleted();
        lastName = user.getLastName();
        firstName = user.getFirstName();
        password = "---";
        permissions = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
