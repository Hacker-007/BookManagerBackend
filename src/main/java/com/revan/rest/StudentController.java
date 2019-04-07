package com.revan.rest;

import com.revan.Response;
import com.revan.dao.BookRepo;
import com.revan.dao.Student;
import com.revan.model.Book;
import com.revan.model.Students;
import com.revan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

// A class that handles all of the GET/POST/DELETE/PUT requests from the frontend
@RestController
@RequestMapping(StudentController.BASE_ADDRESS)
public class StudentController {
    final static String BASE_ADDRESS = "/bookmanager/student";

    @Autowired
    StudentService studentService;

    @Autowired
    Student studentRepo;

    @Autowired
    BookRepo bookRepo;

    @Autowired
    Student student;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Response addUser(@RequestBody Students students) {
        Response response = new Response();
        studentService.addUser(students.getUsername(), students.getPassword(), students.getFirstName(), students.getLastName(), students.getTeacher());
        response.setStatus("Done");
        return response;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response login(@RequestBody Students students){
        Response response = new Response();
        Optional<String> token = studentService.signin(students.getUsername(), students.getPassword());
        if(!token.isPresent()){
            response.setStatus("Wrong Credentials");
        } else {
            response.setStatus("Done");
            response.setData(token.get());
        }
        return response;
    }

    @PostMapping("/checkout/{username}/{title}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response checkout(@PathVariable("username") String username, @PathVariable("title") String title) {
        Response response = new Response();
        if(studentRepo.findByUsername(username).isPresent()) {
            Book book = bookRepo.getBookByTitle(title);
            studentService.checkout(username, book);
            response.setStatus("Done");
        } else {
            response.setStatus("Error");
        }
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/checkin/{username}/{title}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response checkin(@PathVariable("username") String username, @PathVariable("title") String title) {
        Response response = new Response();
        if(studentRepo.findByUsername(username).isPresent()) {
            Book book = bookRepo.getBookByTitle(title);
            studentService.checkin(username, book);
            response.setStatus("Done");
        } else {
            response.setStatus("Error");
        }
        return response;
    }

    @GetMapping("/roles/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getAllRoles(@PathVariable("username") String username) {
        return studentService.getRoles(username);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response getAllUsers() {
        Response response = new Response();
        response.setData(studentService.getAllUsers());
        return response;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public Response getUsersById(@PathVariable("id") int id) {
        Response response = new Response();
        if (!studentService.exists(Students.class, id)) {
            response.setStatus("Error");
        } else {
            response.setStatus("Done");
            response.setData(studentService.getUserById(id));
        }

        return response;
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Response getStudentByUsername(@PathVariable("username") String username) {
        Response response = new Response();
        Optional<Students> optional = student.findByUsername(username);
        if(optional.isPresent()) {
            response.setStatus("Done");
            response.setData(optional.get());
        } else {
            response.setStatus("Error");
        }
        return response;
    }

    @GetMapping("/allBooks/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getAllBooks(@PathVariable("username") String username) {
        Response response = new Response();
        Optional<Students> optional = student.findByUsername(username);
        if(optional.isPresent()) {
            return studentService.getAllBooks(optional.get().getUsername());
        }
        return new ArrayList<>();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public Response deleteUserById(@PathVariable("id") int id) {
        Response response = new Response();
        if (!studentService.exists(Students.class, id)) {
            response.setStatus("Error");
        } else {
            response.setStatus("Done");
            studentService.deleteUserById(id);
        }

        return response;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response changeUserById(@PathVariable("id") int id, @RequestBody Students students) {
        Response response = new Response();
        String pattern = "(\\d{5})";
        if (Pattern.matches(pattern, students.getFirstName())) {
            response.setStatus("Done");
            studentService.changeUserById(id, students.getFirstName(), students.getLastName());
        } else {
            response.setStatus("Error");
        }

        return response;
    }
}