package com.apple.lab8_653380120_2_sec1.Controller.WebController;

import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Models.Book;
import com.apple.lab8_653380120_2_sec1.Service.AuthorService;
import com.apple.lab8_653380120_2_sec1.Service.BookService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/books")
public class WebBookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public WebBookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAllWithAuthors();
        model.addAttribute("books", books);
        return "book/show-all-book";
    }

    @GetMapping("/add")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book/add-book";
    }

    @PostMapping("/add")
    public String saveBook(@ModelAttribute("book") Book book, Model model) {
        book.setAuthor(authorService.findById(book.getAuthor().getId()));
        bookService.save(book);
        model.addAttribute("book", book);
        model.addAttribute("author", authorService.findById(book.getAuthor().getId()));
        return "book/book-information";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAll());
        return "book/edit-book";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") Book updateBook) {
        bookService.save(updateBook);
        return "redirect:/books";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/genre")
    public String getBooksByGenre(String genre, Model model) {
        List<Book> books = bookService.findBooksByGenre(genre);
        model.addAttribute("books", books);
        model.addAttribute("genre", genre);
        return "book/list-books-by-genre";
    }

    @GetMapping("/published-date")
    public String getBooksByPublishedDate(LocalDate publishedDate, Model model) {
        List<Book> books = bookService.findByPublished_date(publishedDate);
        model.addAttribute("books", books);
        model.addAttribute("publishedDate", publishedDate);
        return "book/list-books-by-published-date";
    }
}
