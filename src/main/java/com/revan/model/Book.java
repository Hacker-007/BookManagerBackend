package com.revan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.UUID;

// Represents a book object and places this into the database as a new table
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id", nullable = false, unique = true)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "redemption_code", nullable = false)
    private String code;

    @Column(name = "checked_out", nullable = false)
    private boolean checkedOut = false;

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @JsonIgnore
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Students students;

    public Students getStudent() { return students; }

    public void setStudent(Students students) { this.students = students; }

    public static String createRedemptionCode() {
        return UUID.randomUUID().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
