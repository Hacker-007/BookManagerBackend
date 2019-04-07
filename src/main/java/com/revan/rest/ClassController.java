package com.revan.rest;

import com.revan.Response;
import com.revan.model.Class;
import com.revan.model.Students;
import com.revan.service.ClassService;
import com.revan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// A class that handles all of the GET/POST/DELETE/PUT requests from the frontend
@RestController
@RequestMapping(ClassController.BASE_ADDRESS)
public class ClassController {
    final static String BASE_ADDRESS = "/bookmanager/class";

    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

    @PostMapping("{teacher}")
    @ResponseStatus(HttpStatus.OK)
    public Response addClass(@PathVariable("teacher") String teacher) {
        Response response = new Response();
        classService.addClass(teacher);
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

    @PostMapping("/addStudent/{teacher}/{username}")
//    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseStatus(HttpStatus.OK)
    public Response addStudent(@PathVariable("teacher") String teacher, @PathVariable("username") String username) {
        Response response = new Response();
        response.setStatus("Done");
        classService.addStudent(teacher, username);
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public Response getAllClasses() {
        Response response = new Response();
        response.setData(classService.getAllClasses());
        return response;
    }

    @GetMapping("/allStudents/{teacher}")
    @ResponseStatus(HttpStatus.OK)
    public Response getAllStudents(@PathVariable("teacher") String teacher) {
        Response response = new Response();
        response.setStatus("Done");
        response.setData(classService.getAllStudents(teacher));
        return response;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response getClassById(@PathVariable("id") int id) {
        Response response = new Response();
        if (!classService.exists(Students.class, id)) {
            response.setStatus("Error");
        } else {
            response.setStatus("Done");
            response.setData(classService.getClassById(id));
        }

        return response;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteClassById(@PathVariable("id") String id) {
        Response response = new Response();
        response.setStatus("Done");
        classService.deleteClassById(Integer.parseInt(id));
        return response;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response changeClassById(@PathVariable("id") int id, String teacher) {
        Response response = new Response();
        response.setStatus("Done");
        classService.changeClassById(id, teacher);
        return response;
    }
}