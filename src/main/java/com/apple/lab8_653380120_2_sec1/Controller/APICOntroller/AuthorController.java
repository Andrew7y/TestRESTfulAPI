package com.apple.lab8_653380120_2_sec1.Controller.APICOntroller;

import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Service.AuthorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public @ResponseBody List<Author> getAllAuthor() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Author getAuthorById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody Author addAuthor(@ModelAttribute Author author) {
        return authorService.save(author);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody Author updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        Author updateAuthor = authorService.findById(id);
        if (updateAuthor != null) {
            updateAuthor.setName(author.getName());
            updateAuthor.setEmail(author.getEmail());
            return authorService.save(updateAuthor);
        }
        else{
            return authorService.findById(id);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAuthor(@PathVariable Long id) {
        if (authorService.findById(id) != null) {
            authorService.deleteById(id);
        }
        return String.format("Author Id:%d was delete successfully!", id);
    }


}
