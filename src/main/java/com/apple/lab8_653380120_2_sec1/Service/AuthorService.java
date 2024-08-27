package com.apple.lab8_653380120_2_sec1.Service;

import com.apple.lab8_653380120_2_sec1.Exception.AuthorException;
import com.apple.lab8_653380120_2_sec1.Models.Author;
import com.apple.lab8_653380120_2_sec1.Repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
    }
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(()->new AuthorException(id));
    }
    public Author save(Author author) {
        return authorRepository.save(author);
    }
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

}
