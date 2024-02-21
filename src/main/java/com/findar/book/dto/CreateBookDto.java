package com.findar.book.dto;


import com.findar.book.model.Book;
import lombok.Data;

@Data
public class CreateBookDto {
    private String title;
    private String author;
    private String isbn;
    private double price;

    public Book toEntity(){
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPrice(price);
        return book;
    }
}
