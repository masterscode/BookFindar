package com.findar.book.model;

import com.findar.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE Book SET delFlag='Y' where id=?")
public class Book extends BaseEntity {

    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private double price;
}
