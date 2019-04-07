package com.revan.service;

import com.revan.dao.Student;
import com.revan.dao.StudentRepo;
import com.revan.model.Book;
import com.revan.model.Students;
import com.revan.security.JwtTokenProvider;
import com.revan.security.BookManagerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// A class that passes along the information and data to the student repository
@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private BookManagerUserDetailsService userDetailsService;

    @Autowired
    private Student student;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void addUser(String username,
                        String password,
                        String firstName,
                        String lastName,
                        String teacher){
        studentRepo.saveUser(username, password, firstName, lastName, teacher);
    }

    public List<?> getRoles(String username) { return studentRepo.getRoles(username);   }

    public List<Students> getAllUsers(){
        return studentRepo.getAllUsers();
    }

    public Students getUserById(int id){
        return studentRepo.getUserById(id);
    }

    public void deleteUserById(int id){
        studentRepo.deleteUserById(id);
    }

    public void changeUserById(int id, String firstName, String lastName){
        Students students = studentRepo.getUserById(id);
        changeUserById(id, firstName, lastName, students.getBooks());
    }

    public void changeUserById(int id, String firstName, String lastName, List<Book> books) {
        studentRepo.changeUserById(id, firstName, lastName, books);
    }

    public boolean exists(Class<?> c, Object username){ return studentRepo.exists(c, username); }

    public void checkout(String username, Book book) {
        studentRepo.checkout(username, book);
    }

    public void checkin(String username, Book book) { studentRepo.checkin(username, book); }

    public List<Book> getAllBooks(String username) { return studentRepo.getAllBooks(username); }

    public Optional<String> signin(String username, String password) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        Optional<String> token = Optional.empty();
        if(student.findByUsername(username).isPresent()) {
            if(passwordEncoder.matches(password, userDetailsService.loadUserByUsername(username).getPassword())){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtTokenProvider.createToken(username, student.findByUsername(username).get().getRoles()));
            }
        }
        return token;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(12); }

}
