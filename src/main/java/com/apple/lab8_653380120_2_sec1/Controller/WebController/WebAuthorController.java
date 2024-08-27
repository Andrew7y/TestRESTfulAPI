package com.apple.lab8_653380120_2_sec1.Controller.WebController;

import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class WebAuthorController {
    private final AuthorService authorService;

    public WebAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "author/show-all-author";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, Model model) {
        authorService.save(author);
        model.addAttribute("author", author);
        return "author/author-information";
    }

    @GetMapping("/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "author/add-author";
    }

    @GetMapping("/edit")
    public String editAuthor(@RequestParam("id") Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "author/edit-author";
    }

    @PostMapping("/edit")
    public String editAuthor(@ModelAttribute("author") Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    @PostMapping("/delete")
    public String deleteAuthor(@RequestParam("id") Long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

}
