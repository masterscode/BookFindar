package com.findar.book.controller;


import com.findar.book.dto.CreateBookDto;
import com.findar.book.dto.UpdateBookDto;
import com.findar.book.model.Book;
import com.findar.book.service.BookService;
import com.findar.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody CreateBookDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "0.0") Double price,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyy HH:mm:ss") LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyy HH:mm:ss") LocalDateTime end
    ) {

        return ResponseEntity.ok(
                bookService.getAllBook(author, title, price, start, end, PageRequest.of(page, size))
        );
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> getBook(@PathVariable Long bookId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.getBook(bookId));
    }


    @PatchMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long bookId, @Valid @RequestBody UpdateBookDto dto) {
        return ResponseEntity.ok(
                bookService.updateBook(bookId, dto)
        );
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> deleteBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(
                bookService.deleteBook(bookId)
        );
    }
}
