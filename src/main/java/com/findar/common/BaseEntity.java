
package com.findar.common;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Version
    protected int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    protected String delFlag = "N";

    //    @JsonSerialize(using = CustomSerializers.LocalDateSerializer.class)
    protected LocalDateTime deletedOn;

    @UpdateTimestamp
//    @JsonSerialize(using = CustomSerializers.LocalDateSerializer.class)
    protected LocalDateTime updatedOn;

    @CreationTimestamp
//    @JsonSerialize(using = CustomSerializers.LocalDateSerializer.class)
    protected LocalDateTime dateCreated = LocalDateTime.now();


    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseEntity base) {
            return Objects.equals(base.id, this.id);
        }

        return false;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
