package com.findar.book.service;


import com.findar.book.dto.CreateBookDto;
import com.findar.book.model.Book;
import com.findar.book.repository.BookRepository;
import com.findar.common.ApiResponse;
import com.findar.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public ApiResponse<Book> addBook(CreateBookDto dto) {
        if (bookRepository.existsBookByIsbn(dto.getIsbn())) throw new BadRequestException("Book already exist");

        Book book = bookRepository.save(dto.toEntity());

        return ApiResponse.<Book>builder()
                .message("Book saved successfully")
                .data(book)
                .build();
    }

    @Override
    public ApiResponse<Map<String, Object>> getAllBook(String author, String title, double price, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<Book> books = bookRepository.filterBooks(author, title, price, start, end, pageable);

        Map<String, Object> page = Map.of(
                "size", books.getSize(),
                "page", books.getNumber(),
                "totalPages", books.getTotalPages(),
                "totalElements", books.getTotalElements(),
                "content", books.getContent()
        );


        return ApiResponse.<Map<String, Object>>builder()
                .message("All books retrieved")
                .data(page)
                .build();

    }

    @Override
    public ApiResponse<Book> getBook(Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Unknown book"));

        return ApiResponse.<Book>builder()
                .message("Book retrieved successfully")
                .data(book)
                .build();
    }

    @Override
    public ApiResponse<Book> updateBook(Long bookId, CreateBookDto dto) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Unknown book"));

        book.setPrice(dto.getPrice());
        book.setTitle(dto.getTitle());
        bookRepository.save(book);

        return ApiResponse.<Book>builder()
                .message("Book retrieved successfully")
                .data(book)
                .build();
    }

    @Override
    public ApiResponse<Book> deleteBook(Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Unknown book"));
        bookRepository.delete(book);

        return ApiResponse.<Book>builder()
                .message("Book deleted successfully")
                .data(book)
                .build();
    }


}
