package com.library.api.services;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.BookDto;
import com.library.api.dto.Response;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Book;
import com.library.api.model.BookStatus;
import com.library.api.repository.BookRepository;
import com.library.api.repository.BorrowBookRepository;
import com.library.api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    public static final String BOOK_NOT_FOUND_BY_ID = "Book not found by id : ";
    private final BookRepository bookRepository;
    private final BorrowBookRepository borrowBookRepository;
    private final ModelMapper modelMapper;

    public Response create(BookDto dto) throws ResourceExistException {
        Optional<Book> optionalBook = bookRepository.findByIsbn(dto.getIsbn());
        if (!optionalBook.isPresent()) {
            Book book = modelMapper.map(dto, Book.class);
            Book savedBook = bookRepository.save(setBookStatus(book));
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Book created successfully",
                    modelMapper.map(savedBook, BookDto.class));
        }
        throw new ResourceExistException("Book already exist by isbn: " + dto.getIsbn());
    }

    public Book setBookStatus(Book book) {
        if (book.getNoOfCopies() <= 0) {
            book.setStatus(BookStatus.NOT_AVAILABLE.name());
        } else {
            book.setStatus(BookStatus.AVAILABLE.name());
        }
        return book;
    }

    public Response findAllBooks(Pageable pageable) {
        Page<BookDto> bookPage = bookRepository.findAll(pageable)
                .map(book -> modelMapper.map(book, BookDto.class));
        if (bookPage.hasContent()) {
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK, "All book", bookPage);
        } else {
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK, "No content found", null);
        }
    }

    public Response findById(long id) throws ResourceNotFoundException {
        Book book = getBookById(id);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book found by Id ",
                modelMapper.map(book, BookDto.class));
    }

    public Book getBookById(long id) throws ResourceNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found by id: " + id));
    }

    public Response update(long id, BookDto dto) throws ResourceExistException, ResourceNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Optional<Book> bookByIsbn = bookRepository.findByIsbn(dto.getIsbn());
            if (bookByIsbn.isPresent() && (bookByIsbn.get().getId() != id)) {
                throw new ResourceExistException("Book already exist by isbn: " + dto.getIsbn());
            } else {
                dto.setBookId(id);
                Book book = modelMapper.map(dto, Book.class);
                Book updateBook = bookRepository.save(setBookStatus(book));
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book update",
                        modelMapper.map(updateBook, BookDto.class));
            }
        }
        throw new ResourceNotFoundException(BOOK_NOT_FOUND_BY_ID + id);
    }

    public Response deleteByBookId(long id) throws ResourceNotFoundException {
        if (bookRepository.existsById(id)) {
            borrowBookRepository.deleteAllByBook_Id(id);
            bookRepository.deleteById(id);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book deleted by id: " + id, null);
        }
        throw new ResourceNotFoundException(BOOK_NOT_FOUND_BY_ID + id);
    }
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
