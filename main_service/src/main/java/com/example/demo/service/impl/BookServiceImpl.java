package com.example.demo.service.impl;

import com.example.demo.constant.Status;
import com.example.demo.dto.BookGeneral;
import com.example.demo.dto.request.BookRequest;
import com.example.demo.dto.response.BaseResponse;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

    // Repository
    private final BookRepository bookRepository;

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public BaseResponse create(BookRequest bookRequest) {
        Book book = new Book();

        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setIsActive(Boolean.TRUE);

        bookRepository.save(book);

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource));
    }

    @Override
    @Transactional
    public BaseResponse update(String bookId, BookRequest bookRequest) {
        Optional<Book> optionalBook = bookRepository.findById(Integer.valueOf(bookId));
        if (!optionalBook.isPresent()) {
            return new BaseResponse(Status.ITEM_NOT_FOUND, Status.getMessage(Status.ITEM_NOT_FOUND, null, messageSource));
        }

        Book book = optionalBook.get();

        book.setTitle(bookRequest.getTitle() == null ? book.getTitle() : bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor() == null ? book.getAuthor() : bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription() == null ? book.getDescription() : bookRequest.getDescription());
        book.setPublishDate(bookRequest.getPublishDate() == null ? book.getPublishDate() : bookRequest.getPublishDate());

        bookRepository.save(book);

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource));
    }

    @Override
    @Transactional
    public BaseResponse delete(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(Integer.valueOf(bookId));
        if (!optionalBook.isPresent()) {
            return new BaseResponse(Status.ITEM_NOT_FOUND, Status.getMessage(Status.ITEM_NOT_FOUND, null, messageSource));
        }

        Book book = optionalBook.get();
        book.setIsActive(Boolean.FALSE);

        bookRepository.save(book);

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource));
    }

    @Override
    public BaseResponse getAll() {
        List<Book> books = bookRepository.getAll();
        if (books.isEmpty()) {
            return new BaseResponse(Status.ITEM_LIST_EMPTY, Status.getMessage(Status.ITEM_LIST_EMPTY, null, messageSource));
        }
        List<BookGeneral> bookGenerals = books.stream().map(b -> new BookGeneral(
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getPublishDate(),
                b.getDescription(),
                b.getIsActive()))
                .collect(Collectors.toList());

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource), bookGenerals);
    }

    @Override
    public BaseResponse getById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            return new BaseResponse(Status.ITEM_NOT_FOUND, Status.getMessage(Status.ITEM_NOT_FOUND, null, messageSource));
        }
        BookGeneral bookGeneral = objectMapper.convertValue(book, BookGeneral.class);

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource), bookGeneral);
    }
}
