package com.findar.book.dto;


import com.findar.book.enums.BookStatus;
import com.findar.book.model.Book;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookDto {
    @NotBlank(message = " is required")
    private String title;
    @NotBlank(message = " is required")
    private String author;
    @NotBlank(message = " is required")
    private String isbn;
    @DecimalMin(value = "100.0")
    @NotNull(message = " is required")
    private Double price;

    public Book toEntity() {
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setStatus(BookStatus.AVAILABLE);
        book.setPrice(price);
        return book;
    }
}
