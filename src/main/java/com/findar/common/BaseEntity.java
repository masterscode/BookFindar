
package com.findar.common;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.findar.config.JsonDateSerializer;
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

    @Column(columnDefinition = "bit default 0", nullable = false)
    protected boolean deleted = false;

    @UpdateTimestamp
    @JsonSerialize(using = JsonDateSerializer.LocalDateTimeSerializer.class)
    protected LocalDateTime updatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return version == that.version
                && deleted == that.deleted
                && Objects.equals(id, that.id)
                && Objects.equals(updatedOn, that.updatedOn)
                && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, deleted, updatedOn, dateCreated);
    }

    @CreationTimestamp
    @JsonSerialize(using = JsonDateSerializer.LocalDateTimeSerializer.class)
    protected LocalDateTime dateCreated = LocalDateTime.now();

}
