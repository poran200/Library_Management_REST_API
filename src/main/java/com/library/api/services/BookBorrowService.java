package com.library.api.services;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.BookBorrowDto;
import com.library.api.dto.BookBorrowRequestDto;
import com.library.api.dto.Response;
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
    public Response borrowedABook(BookBorrowRequestDto dto) throws ResourceNotFoundException {
        User user = userService.getUserByName(dto.getUsername());
        Book book = bookService.getBookById(dto.getBookId());
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBook(book);
        borrowBook.setStudent(user);
        if (dto.getReturnDate() == null) {
            borrowBook.setReturnDate(getSevenDaysDate());
        } else {
            borrowBook.setReturnDate(dto.getReturnDate());
        }
        borrowBook.setStatus(BorrowStatus.BORROWED.name());
        BorrowBook borrowBookSaved = borrowBookRepository.save(borrowBook);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Book borrow successfully",
                modelMapper.map(borrowBookSaved, BookBorrowDto.class));
    }

    public Response update(long borrowId, BookBorrowRequestDto dto) throws ResourceNotFoundException {
        Optional<BorrowBook> borrowBookOptional = borrowBookRepository.findById(borrowId);
        if (borrowBookOptional.isPresent()) {
            BorrowBook borrowBook = borrowBookOptional.get();
            if (!borrowBook.getBook().getId().equals(dto.getBookId())) {
                borrowBook.setBook(bookService.getBookById(dto.getBookId()));
            } else if (!borrowBook.getStudent().getUsername().equalsIgnoreCase(dto.getUsername())) {
                borrowBook.setStudent(userService.getUserByName(dto.getUsername()));
            } else {
                if (StringUtils.isNotBlank(dto.getStatus())) {
                    borrowBook.setStatus(dto.getStatus().toUpperCase());
                }
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Book borrow Updated",
                    modelMapper.map(borrowBook, BookBorrowDto.class));
        }
        throw new ResourceNotFoundException(" Borrow book not found by Id: " + borrowId);
    }

     public Response findById(long borrowId) throws ResourceNotFoundException {
         BorrowBook borrowBook = borrowBookRepository.findById(borrowId).orElseThrow(() -> new ResourceNotFoundException(" Borrow book not found by Id: " + borrowId));
         return ResponseBuilder.getSuccessResponseWithType(HttpStatus.OK,"Borrow book found!",
                 modelMapper.map(borrowBook,BookBorrowDto.class));
     }
     public Response findAll(Pageable pageable){
         Page<BookBorrowDto> borrowDtoPage = borrowBookRepository.findAll(pageable)
                 .map(borrowBook -> modelMapper.map(borrowBook, BookBorrowDto.class));
         return  ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"All borrow books",borrowDtoPage);
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
