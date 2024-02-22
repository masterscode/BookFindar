package com.findar.book.model;

import com.findar.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
//@Table(name = "books")
//@SQLDelete(sql = "UPDATE book SET del_flag='Y' where id = ?1")
public class Book extends BaseEntity {

    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private double price;
}
