package com.apple.lab8_653380120_2_sec1.Repository;

import com.apple.lab8_653380120_2_sec1.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
