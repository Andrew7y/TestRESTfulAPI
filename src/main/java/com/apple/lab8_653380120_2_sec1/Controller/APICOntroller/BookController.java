package com.apple.lab8_653380120_2_sec1.Controller.APICOntroller;

import com.apple.lab8_653380120_2_sec1.Exception.BookException;
import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Models.Book;
import com.apple.lab8_653380120_2_sec1.Service.AuthorService;
import com.apple.lab8_653380120_2_sec1.Service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/api/authors/{authorId}/books")
    public @ResponseBody List<Book> getAllBooks(@PathVariable Long authorId) {
        Author author = authorService.findById(authorId);
        return author.getBook();
    }

    @PostMapping(value = "/api/authors/{authorId}/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody Book createBook(@PathVariable Long authorId,
                                         @ModelAttribute Book book)
    {
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        return bookService.save(book);
    }

    @GetMapping("/api/authors/{authorId}/books/{id}")
    public @ResponseBody Book getBook(@PathVariable Long authorId, @PathVariable Long id){
        Author author = authorService.findById(authorId);
        Book book = bookService.findById(id);
        return bookService.findBookInAuthorBooks(book, author).get();
    }

    @PutMapping(value = "/api/authors/{authorId}/books/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody Book updateBook(@PathVariable Long id,
                                         @PathVariable Long authorId,
                                         @ModelAttribute Book updateBook)
    {
        Author author = authorService.findById(authorId);
        Book oldBook = bookService.findById(id);
        bookService.findBookInAuthorBooks(oldBook, author);
        return bookService.updateBook(oldBook, updateBook);
    }

    @DeleteMapping("/api/authors/{authorId}/books/{id}")
    public @ResponseBody String deleteBook(@PathVariable Long authorId, @PathVariable Long id){
        try {
            Author author = authorService.findById(authorId); //ตรวจสอบว่าผู้แต่งคนนี้มีในฐานข้อมูลหรือไม่
            Book book = bookService.findById(id); //ตรวจสอบว่าหนังสือเล่มนี้มีในฐานข้อมูลหรือไม่
            bookService.findBookInAuthorBooks(book, author); // ตรวจสอบว่าผู้แต่งคนนี้มีหนังสืออยู่หรือป่าว
            bookService.deleteById(id); //ลบ

            //ค้นหาว่าหนังสือถูกลบจริงหรือไม่ถ้าถูกลบจริง findById() จะมีการ trowException
            bookService.findById(id);
            return "Something went wrong while deleting the book.";
        }catch (BookException ex){

            //ถ้าจับ Exception ได้แปลว่าหนังสือถูกลบจริง
            return String.format("The book with id:%d has been successfully deleted.", id);
        }
    }

    @GetMapping("/api/books/genre/{genre}")
    public @ResponseBody List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.findBooksByGenre(genre);
    }

    @GetMapping("/api/books/publishedDate/{publishedDate}")
    public @ResponseBody List<Book> getBooksByPublishedDate(@PathVariable LocalDate publishedDate) {
        return bookService.findByPublished_date(publishedDate);
    }


}
