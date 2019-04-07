package com.revan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Represents a class object and places this into the database as a new table
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false, unique = true)
    private int id;

    @Column(name = "teacher", nullable = false)
    private String teacher;

    @JsonIgnore
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "teacher_role",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "r_id"))
    private List<Role> roles;

    @JsonIgnore
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Students> students = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
}
