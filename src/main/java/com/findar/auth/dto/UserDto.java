package com.findar.auth.dto;


import com.findar.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private Long id;

    private boolean deleted;


    private Collection<String> permissions;

    protected String dateCreated;

    public UserDto(User user) {
        id = user.getId();
        dateCreated = String.valueOf(user.getDateCreated());
        deleted = user.isDeleted();
        lastName = user.getLastName();
        firstName = user.getFirstName();
        password = user.getPassword();
        permissions = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
