package com.findar.book.repository;

import com.findar.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where (:author='' or b.author=:author) AND (:title ='' or b.title like '%'||:title||'%') " +
            " AND (:price=0 or b.price = :price) " +
            " AND ((:start is null or :end is null ) or (date(b.dateCreated) between date(:start) and date(:end)))")
    Page<Book> filterBooks(String author, String title, Double price, LocalDateTime start, LocalDateTime end, Pageable pageable);

    boolean existsBookByIsbn(String isbn);
}
