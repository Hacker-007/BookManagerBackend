package com.revan.dao;

import com.revan.model.Book;
import com.revan.model.Role;
import com.revan.model.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public class StudentRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Student studentRepo;

    @Autowired
    private BookRepo bookRepo;

    private Session getSession() {
        return sessionFactory.openSession();
    }


    public void saveUser(String username, String password, String firstName,
                            String lastName, String teacher) {
        Session session = getSession();
        Students students = new Students();
        students.setUsername(username);
        students.setPassword(passwordEncoder().encode(password));
        students.setFirstName(firstName);
        students.setLastName(lastName);
        students.setTeacher(teacher);
        List<Role> defaultRole = new ArrayList<>();
        defaultRole.add((Role) session.createCriteria(Role.class).add(Restrictions.eq("id", 3)).uniqueResult());
        students.setRoles(defaultRole);
        session.save(students);
        session.close();
    }


    public List<Students> getAllUsers(){
        Session session = getSession();
        List<Students> students = (List<Students>) session.createCriteria(Students.class).list();
        session.close();
        return students;
    }

    public Students getUserById(int id){
        Session session = getSession();
        Students students = (Students) session.createCriteria(Students.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        return students;
    }

    public void deleteUserById(int id){
        Session session = getSession();
        Students students = session.byId(Students.class).load(id);
        session.delete(students);
        session.close();
    }

    public void changeUserById(int id, String firstName, String lastName, List<Book> books){
        Session session = getSession();
        Students students = session.get(Students.class, id);
        students.setFirstName(firstName);
        students.setLastName(lastName);
        students.setBooks(books);
        session.close();
    }

    public boolean exists(Class<?> c, Object username){
        Session session = getSession();
        boolean bool = session.createCriteria(c)
                .add(Restrictions.eq("username", username))
                .uniqueResult() != null;
        session.close();
        return bool;
    }

    public List<Book> getAllBooks(String username) {
        Session session = getSession();
        List<Students> studentsList = session.createCriteria(Students.class).add(Restrictions.eq("username", username)).list();
        if(studentsList.size() == 0) {
            return new ArrayList<>();
        }
        Students students = studentsList.get(0);
        List<Book> books = students.getBooks();
        session.close();
        return books;
    }

    // Checking out a book by adding this book to the students existing books
    public void checkout(String username, Book book) {
        Session session = getSession();
        Students students = (Students) session.createCriteria(Students.class).add(Restrictions.eq("username", username)).list().get(0);
        List<Book> books = students.getBooks();
        books.add(book);
        students.setBooks(books);
        bookRepo.changeBookById(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), true, students, session);
        session.close();
    }

    // Checking in a book by removing this book from the students checked out books
    public void checkin(String username, Book book) {
        Session session = getSession();
        Students students = (Students) session.createCriteria(Students.class).add(Restrictions.eq("username", username)).uniqueResult();
        List<Book> books = students.getBooks();
        books.remove(book);
        students.setBooks(books);
        bookRepo.changeBookById(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), false, Book.createRedemptionCode(), null, session);
        session.close();
    }

    public List<Role> getRoles(String username) {
        Session session = getSession();
        Students students = (Students) session.createCriteria(Students.class).add(Restrictions.eq("username", username)).uniqueResult();
        return students.getRoles();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}