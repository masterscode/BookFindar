package com.findar.book.model;

import com.findar.book.enums.BookStatus;
import com.findar.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "books")
@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE books SET deleted= true where id = ? and version = ?")
public class Book extends BaseEntity {

    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private double price;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
