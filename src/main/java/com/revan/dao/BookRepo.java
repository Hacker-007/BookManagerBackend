package com.revan.dao;

import com.revan.model.Book;
import com.revan.model.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

// A repository that will insert data into the SQL database
@Repository
@Transactional
public class BookRepo {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    // This method will save a new book into the database
    public void saveBook(String title, String author, String isbn) {
        Session session = getSession();
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCode(Book.createRedemptionCode());
        book.setCheckedOut(false);
        book.setStudent(null);
        session.save(book);
        session.close();
    }

    // Get all the books by getting a list of all the books in the SQL database
    public List<?> getAllBooks(){
        Session session = getSession();
        List<?> books = session.createCriteria(Book.class).list();
        session.close();
        return books;
    }

    // Getting a single book by adding a restriction: title
    public Book getBookByTitle(String title) {
        Session session = getSession();
        Book book = (Book) session.createCriteria(Book.class).add(Restrictions.eq("title", title)).uniqueResult();
        session.close();
        return book;
    }

    // Getting the book using the specific id of the book
    public Book getBookById(int id){
        Session session = getSession();
        // Need to cast to a book object because the method uniqueResult returns an Object
        Book book = (Book) session.createCriteria(Book.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        return book;
    }

    // Deleting a book by its id using the sessions built-in-method delete
    public void deleteBookById(int id){
        Session session = getSession();
        Book book = session.byId(Book.class).load(id);
        session.delete(book);
        session.close();
    }

    // Changing a book by its id
    // Changing it by updating all of its values
    public void changeBookById(int id, String title, String author, String isbn, boolean checkedOut){
        Session session = getSession();
        Book book = session.get(Book.class, id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCheckedOut(checkedOut);
        session.close();
    }

    // Creating an overloaded method that will update the book with everything, including a student
    public void changeBookById(int id, String title, String author, String isbn, boolean checkedOut, Students students, Session session) {
        Book book = session.get(Book.class, id);
        Transaction tx = session.beginTransaction();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCheckedOut(checkedOut);
        book.setStudent(students);
        session.update(book);
        tx.commit();
    }

    // This method also changes the redemption code. Useful for checking in a book, when a new redemption code may be neede
    public void changeBookById(int id, String title, String author, String isbn, boolean checkedOut, String redemptionCode, Students students, Session session) {
        Book book = session.get(Book.class, id);
        Transaction tx = session.beginTransaction();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCheckedOut(checkedOut);
        book.setCode(redemptionCode);
        book.setStudent(students);
        session.update(book);
        tx.commit();
    }

    // Checking if the book exists, using the title
    public boolean exists(Class<?> c, Object title){
        Session session = getSession();
        boolean exists = session.createCriteria(c)
                .add(Restrictions.eq("title", title))
                .uniqueResult() != null;
        session.close();
        return exists;
    }
}