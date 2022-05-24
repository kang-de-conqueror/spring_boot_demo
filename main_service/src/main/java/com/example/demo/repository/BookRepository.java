package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT b.id, b.title, b.author, b.publish_date, b.description, b.is_active FROM book b WHERE b.is_active = 1 AND b.id = :book_id", nativeQuery = true)
    Optional<Book> getById(@Param("book_id") String bookId);

    @Query(value = "SELECT b.id, b.title, b.author, b.publish_date, b.description, b.is_active FROM book b WHERE b.is_active = 1", nativeQuery = true)
    List<Book> getAll();
}
