package com.library.api.services;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.BookBorrowDto;
import com.library.api.dto.BookBorrowRequestDto;
import com.library.api.dto.Response;
import com.library.api.exception.BookNotAvailableException;
import com.library.api.exception.BorrowBookNoUpdateException;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Book;
import com.library.api.model.BorrowBook;
import com.library.api.model.BorrowStatus;
import com.library.api.model.User;
import com.library.api.repository.BorrowBookRepository;
import com.library.api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookBorrowService {
    private final UserService userService;
    private final BookService bookService;
    private final BorrowBookRepository borrowBookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Response borrowedABook(BookBorrowRequestDto dto) throws ResourceNotFoundException, ResourceExistException, BookNotAvailableException {
        validateBorrowRequest(dto.getBookId(),dto.getUsername());
        User user = userService.getUserByName(dto.getUsername());
        Book book = bookService.getBookById(dto.getBookId());
        if (!book.isAvailable()) throw new BookNotAvailableException("Book not available for borrow bookId: "+dto.getBookId());
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBook(book);
        borrowBook.setStudent(user);
        if (dto.getReturnDate() == null) {
            borrowBook.setReturnDate(getSevenDaysDate());
        } else {
            borrowBook.setReturnDate(dto.getReturnDate());
        }
        borrowBook.setStatus(BorrowStatus.BORROWED.name());
        book.decrementNoOfCopy();
        bookService.saveBook(book);
        BorrowBook borrowBookSaved = borrowBookRepository.save(borrowBook);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Book borrow successfully",
                modelMapper.map(borrowBookSaved, BookBorrowDto.class));
    }

    private void validateBorrowRequest(Long bookId, String username) throws ResourceExistException {
        Optional<BorrowBook> byBook_idAndStudent_username = borrowBookRepository.findByBook_IdAndStudent_Username(bookId, username);
        if (byBook_idAndStudent_username.isPresent()){
            throw new ResourceExistException("Student Already borrow this book :"+bookId);
        }
    }

    @Transactional
    public Response update(long borrowId, BookBorrowRequestDto dto) throws ResourceNotFoundException, BorrowBookNoUpdateException {
        Optional<BorrowBook> borrowBookOptional = borrowBookRepository.findById(borrowId);
        if (borrowBookOptional.isPresent()) {
            BorrowBook borrowBook = borrowBookOptional.get();
            borrowBookRepository.save(validateBorrowBookAndUpdate(dto, borrowBook));
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book borrow Updated",
                    modelMapper.map(borrowBook, BookBorrowDto.class));
        }
        throw new ResourceNotFoundException(" Borrow book not found by Id: " + borrowId);
    }

    public BorrowBook validateBorrowBookAndUpdate(BookBorrowRequestDto dto, BorrowBook borrowBook) throws BorrowBookNoUpdateException, ResourceNotFoundException {
        if (!borrowBook.getBook().getId().equals(dto.getBookId())) {
            if (borrowBook.getStatus().equalsIgnoreCase(BorrowStatus.RETURNED.name())){
                throw new BorrowBookNoUpdateException("Can not update the book if you already return it");
            }
            // when student borrow the book but not return it and try to update the book
            Book updateBook = borrowBook.getBook();
            updateBook.incrementNoOfCopy();
            bookService.saveBook(updateBook);
            // set the update book
            Book bookById = bookService.getBookById(dto.getBookId());
            borrowBook.setBook(bookById);
        } else if (!borrowBook.getStudent().getUsername().equalsIgnoreCase(dto.getUsername())) {
            borrowBook.setStudent(userService.getUserByName(dto.getUsername()));
        } else {
            if (StringUtils.isNotBlank(dto.getStatus()) && !borrowBook.getStatus().equals(BorrowStatus.RETURNED.name())) {
                borrowBook.setStatus(getStatus(dto.getStatus(), borrowBook.getBook().getId()).name());
            }

        }
        return borrowBook;
    }

    // update the book status if borrower return the book and also update the book availability
    private BorrowStatus getStatus(String status, Long bookId) throws ResourceNotFoundException {
        BorrowStatus requestStatus = BorrowStatus.valueOf(status.toUpperCase());
        if (requestStatus == BorrowStatus.RETURNED) {
            updateBookStatus(requestStatus, bookId);
        } else {
            return BorrowStatus.BORROWED;
        }
        return requestStatus;
    }

    private void updateBookStatus(BorrowStatus requestStatus, Long bookId) throws ResourceNotFoundException {
        Book book = bookService.getBookById(bookId);
        if (requestStatus == BorrowStatus.BORROWED) {
            book.decrementNoOfCopy();
            bookService.saveBook(book);
        } else if (requestStatus == BorrowStatus.RETURNED) {
            book.incrementNoOfCopy();
            bookService.saveBook(book);
        }
    }

    public Response findById(long borrowId) throws ResourceNotFoundException {
        BorrowBook borrowBook = borrowBookRepository.findById(borrowId).orElseThrow(() -> new ResourceNotFoundException(" Borrow book not found by Id: " + borrowId));
        return ResponseBuilder.getSuccessResponseWithType(HttpStatus.OK, "Borrow book found!",
                modelMapper.map(borrowBook, BookBorrowDto.class));
    }

    public Response findAll(Pageable pageable) {
        Page<BookBorrowDto> borrowDtoPage = borrowBookRepository.findAll(pageable)
                .map(borrowBook -> modelMapper.map(borrowBook, BookBorrowDto.class));
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK, "All borrow books", borrowDtoPage);
    }

    public Response deletedborrowBook(long id) throws ResourceNotFoundException {
        if (borrowBookRepository.existsById(id)) {
            borrowBookRepository.deleteById(id);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book borrow Deleted", null);
        }
        throw new ResourceNotFoundException("Borrow not found by id : " + id);
    }

    private Date getSevenDaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7); //add number would increment the days
        return cal.getTime();
    }
}
