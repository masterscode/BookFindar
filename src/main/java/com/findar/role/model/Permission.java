package com.findar.role.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.findar.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permissions")
public class Permission  extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -344143629178093163L;

    @Column(unique = true)
    private String name;
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> role;

}
