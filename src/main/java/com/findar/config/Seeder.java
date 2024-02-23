package com.findar.config;

import com.findar.role.model.Permission;
import com.findar.role.model.Role;
import com.findar.role.repository.PermissionRepository;
import com.findar.role.repository.RoleRepository;
import com.findar.user.model.User;
import com.findar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        userRepository.fetchUserAndRole("findar@findar.com")
                .ifPresentOrElse(user -> {
                }, () -> {
                    final Role role = seedRole(Set.of(seedPermission()));
                    seedUser(role);
                });

    }

    private Role seedRole(Set<Permission> permissions) {
        Role role = new Role();
        role.setName("USER");
        role.setPermissions(permissions);

        Role savedRole =  roleRepository.save(role);
        Collection<Permission> p = permissions.stream()
                .peek(permission -> permission.setRole(Set.of(savedRole)))
                .collect(Collectors.toSet());
        permissionRepository.saveAll(p);
        return savedRole;
    }

    private Permission seedPermission() {
        Permission permission = new Permission();
        permission.setName("CRUD");
        permission.setDescription("Permission to perform CRUD operations");
        return permissionRepository.save(permission);
    }

    private void seedUser(Role role) {
        User user = new User();
        user.setUsername("findar@findar.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(role);

        userRepository.save(user);
    }
}
