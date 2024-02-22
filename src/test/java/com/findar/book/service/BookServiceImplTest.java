package com.findar.book.service;

import com.findar.book.dto.CreateBookDto;
import com.findar.book.dto.UpdateBookDto;
import com.findar.book.model.Book;
import com.findar.book.repository.BookRepository;
import com.findar.common.ApiResponse;
import com.findar.exception.BadRequestException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl underTest;

    @Test
    void shouldThrowExceptionForExistingBook() {
        CreateBookDto dto = new CreateBookDto();
        dto.setIsbn("mocked-isbn");
        Mockito.when(bookRepository.existsBookByIsbn(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(BadRequestException.class, () -> underTest.addBook(dto));
    }

    @Test
    void shouldSaveBookWithRequestData() {
        CreateBookDto dto = new CreateBookDto();
        dto.setIsbn("mocked-isbn");
        dto.setPrice(1500.0);
        dto.setTitle("Gang of four");
        dto.setAuthor("Erich Gamma");
        Mockito.when(bookRepository.existsBookByIsbn(Mockito.anyString())).thenReturn(false);

        underTest.addBook(dto);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book captured = bookArgumentCaptor.getValue();
        Assertions.assertEquals(dto.getIsbn(), captured.getIsbn());
        Assertions.assertEquals(dto.getPrice(), captured.getPrice());
        Assertions.assertEquals(dto.getTitle(), captured.getTitle());
        Assertions.assertEquals(dto.getAuthor(), captured.getAuthor());

    }

    @Test
    void shouldTestCorrectResponseIsReturnedToWebLayer() {
        CreateBookDto dto = new CreateBookDto();
        dto.setIsbn("mocked-isbn");
        dto.setPrice(1500.0);
        dto.setTitle("Gang of four");
        dto.setAuthor("Erich Gamma");
        Mockito.when(bookRepository.existsBookByIsbn(Mockito.anyString())).thenReturn(false);

        Book mockedBook = dto.toEntity();
        mockedBook.setDateCreated(LocalDateTime.now());
        mockedBook.setId(1L);
        mockedBook.setDelFlag("N");

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(mockedBook);
        ApiResponse<Book> result = underTest.addBook(dto);


        Book data = result.getData();
        Assertions.assertNull(result.getError());
        Assertions.assertEquals(mockedBook.getId(), data.getId());
        Assertions.assertEquals(mockedBook.getDateCreated(), data.getDateCreated());
        Assertions.assertEquals(dto.getIsbn(), data.getIsbn());
        Assertions.assertEquals(dto.getPrice(), data.getPrice());
        Assertions.assertEquals(dto.getTitle(), data.getTitle());
        Assertions.assertEquals(dto.getAuthor(), data.getAuthor());
    }

    @Test
    void shouldReturnEmptyContentIfNoMatchFound() {
        Mockito.when(bookRepository.filterBooks(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(),
                        Mockito.any(), Mockito.any(), Mockito.any())
                )
                .thenReturn(new PageImpl<>(List.of()));

        ApiResponse<Map<String, Object>> result = underTest.getAllBook("", "", 0.0,
                LocalDateTime.now(), LocalDateTime.now(), Pageable.ofSize(10));


        Assertions.assertNull(result.getError());
        Assertions.assertTrue(((List<?>) result.getData().get("content")).isEmpty());


    }

    @Test
    void shouldThrowExceptionForNonExistentBook() {
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BadRequestException.class, () -> underTest.getBook(1L));
    }

    @Test
    void shouldReturnFountBook() {
        Book book = new Book();
        book.setIsbn("mocked-isbn");
        book.setPrice(1500.0);
        book.setTitle("Gang of four");
        book.setAuthor("Erich Gamma");

        book.setDateCreated(LocalDateTime.now());
        book.setId(1L);
        book.setDelFlag("N");
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));


        ApiResponse<Book> result = underTest.getBook(1L);

        Book data = result.getData();
        Assertions.assertNull(result.getError());
        Assertions.assertEquals(book.getId(), data.getId());
        Assertions.assertEquals(book.getDateCreated(), data.getDateCreated());
        Assertions.assertEquals(book.getIsbn(), data.getIsbn());
        Assertions.assertEquals(book.getPrice(), data.getPrice());
        Assertions.assertEquals(book.getTitle(), data.getTitle());
        Assertions.assertEquals(book.getAuthor(), data.getAuthor());

    }


    @Test
    void shouldThrowExceptionForNonExistentBookForUpdate() {
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BadRequestException.class, () -> underTest.updateBook(1L, new UpdateBookDto()));
    }
    @Test
    void shouldUpdateBook() {
        UpdateBookDto dto = new UpdateBookDto();
        dto.setPrice(3500.0);
        dto.setTitle("GoF");

        Book book = new Book();
        book.setIsbn(RandomStringUtils.randomAlphanumeric(8));
        book.setPrice(1500.0);
        book.setTitle("Gang of four");
        book.setAuthor("Erich Gamma");

        book.setDateCreated(LocalDateTime.now());
        book.setId(1L);
        book.setDelFlag("N");
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));


        underTest.updateBook(1L, dto);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());

        Book captured = bookArgumentCaptor.getValue();
        Assertions.assertEquals(dto.getPrice(), captured.getPrice());
        Assertions.assertEquals(dto.getTitle(), captured.getTitle());
    }

    @Test
    void deleteBook() {
        Book book = new Book();
        book.setIsbn(RandomStringUtils.randomAlphanumeric(8));
        book.setPrice(1500.0);
        book.setTitle("Gang of four");
        book.setAuthor("Erich Gamma");

        book.setDateCreated(LocalDateTime.now());
        book.setId(1L);
        book.setDelFlag("N");
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        underTest.deleteBook(1L);

        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }
}