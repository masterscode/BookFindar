package com.findar.role.model;

import com.findar.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;

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



}
