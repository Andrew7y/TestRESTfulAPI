package com.apple.lab8_653380120_2_sec1.Service;

import com.apple.lab8_653380120_2_sec1.Exception.BookException;
import com.apple.lab8_653380120_2_sec1.Exception.BookNotFoundException;
import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Models.Book;
import com.apple.lab8_653380120_2_sec1.Repository.AuthorRepository;
import com.apple.lab8_653380120_2_sec1.Repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository , AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Transactional
    public List<Book> findAllWithAuthors() {
        return (List<Book>) bookRepository.findAllWithAuthors();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookException(id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findBookInAuthorBooks(Book book, Author author) {
        return Optional.ofNullable(author.getBook().stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst().orElseThrow(() ->
                        new BookNotFoundException("Book with ID:"
                        +book.getId()
                        +" not found in the author's collection")));
    }
    public Book updateBook(Book oldBook, Book updateBook) {
        oldBook.setTitle(updateBook.getTitle());
        oldBook.setIsbn(updateBook.getIsbn());
        oldBook.setPublishedDate(updateBook.getPublishedDate());
        oldBook.setGenre(updateBook.getGenre());
        return bookRepository.save(oldBook);
    }
    @Transactional
    public List<Book> findBooksByGenre(String genre) {
        authorService.findAll(); //เรียกใช้เพื่อให้เกิดการเปิด session :) แก้ปัญหาเฉาะหน้าไปก่อน ~
        return bookRepository.findByGenre(genre);
    }
    @Transactional
    public List<Book> findByPublished_date(LocalDate publishedDate) {
        authorService.findAll(); //เรียกใช้เพื่อให้เกิดการเปิด session :) แก้ปัญหาเฉาะหน้าไปก่อน ~
        return bookRepository.findByPublishedDate(publishedDate);
    }


}
