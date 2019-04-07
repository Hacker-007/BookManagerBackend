package com.revan.service;

import com.revan.dao.BookRepo;
import com.revan.model.Book;
import com.revan.security.BookManagerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// A class that passes along the information to the repositories to manage
@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public void addBook(String title, String author, String isbn) {
        bookRepo.saveBook(title, author, isbn);
    }

    public List<?> getAllBooks() {
        return bookRepo.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookRepo.getBookById(id);
    }

    public void deleteBookById(int id) {
        bookRepo.deleteBookById(id);
    }

    public void changeBookById(int id, String title, String author, String isbn, boolean checkedOut) {
        bookRepo.changeBookById(id, title, author, isbn, checkedOut);
    }

    public boolean exists(Class<?> c, Object title) {
        return bookRepo.exists(c, title);
    }
}
