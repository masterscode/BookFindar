package com.findar.book.service;

import com.findar.book.dto.CreateBookDto;
import com.findar.book.dto.UpdateBookDto;
import com.findar.book.model.Book;
import com.findar.common.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

public interface BookService {
    ApiResponse<Book> addBook(CreateBookDto dto);

    ApiResponse<Map<String, Object>> getAllBook(String author, String title, double price, LocalDateTime start, LocalDateTime end, Pageable pageable);

    ApiResponse<Book> getBook(Long bookId);

    ApiResponse<Book> updateBook(Long bookId, UpdateBookDto dto);

    ApiResponse<Book> deleteBook(Long bookId);
}
