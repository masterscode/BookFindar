/*
 * *
 *  * Created by Kolawole Omirin
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 8/23/23, 1:54 PM
 *
 */

package com.findar.role.model;

import com.findar.common.BaseEntity;
import com.findar.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "findar_roles")
public class Role extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -6673617473497518116L;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

}
