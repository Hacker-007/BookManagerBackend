package com.revan.rest;

import com.revan.Response;
import com.revan.model.Book;
import com.revan.model.Students;
import com.revan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// A class that handles all of the GET/POST/DELETE/PUT requests from the frontend
@RestController
@RequestMapping(BookController.BASE_ADDRESS)
public class BookController {
    final static String BASE_ADDRESS = "/bookmanager/book";

    @Autowired
    BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    // Checking if the user is an admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addBook(@RequestBody Book book) {
        Response response = new Response();
        bookService.addBook(book.getTitle(), book.getAuthor(), book.getIsbn());
        response.setStatus("Done");
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<?> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getBookById(@PathVariable("id") int id) {
        Response response = new Response();
        if (!bookService.exists(Book.class, id)) {
            response.setStatus("Error");
        } else {
            response.setStatus("Done");
            response.setData(bookService.getBookById(id));
        }

        return response;
    }

    // Using path variables to receive input through the path
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteBookById(@PathVariable("id") int id) {
        Response response = new Response();
        if (!bookService.exists(Students.class, id)) {
            response.setStatus("Error");
        } else {
            response.setStatus("Done");
            bookService.deleteBookById(id);
        }

        return response;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response changeBookById(@PathVariable("id") int id, @RequestBody Book book) {
        Response response = new Response();
        bookService.changeBookById(id, book.getTitle(), book.getAuthor(), book.getIsbn(), book.isCheckedOut());
        return response;
    }
}