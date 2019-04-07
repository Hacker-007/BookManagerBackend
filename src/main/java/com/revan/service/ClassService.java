package com.revan.service;

import com.revan.dao.ClassRepo;
import com.revan.model.Class;
import com.revan.model.Students;
import com.revan.security.BookManagerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// A class that passes along the information and data to the repository
@Service
public class ClassService {

    @Autowired
    private ClassRepo classRepo;

    public void addClass(String teacher) {
        classRepo.createClass(teacher);
    }
    public List<Class> getAllClasses() { return classRepo.getAllClasses(); }
    public Class getClassById(int id) { return classRepo.getClassById(id); }
    public void deleteClassById(int id) { classRepo.deleteClassById(id); }
    public void changeClassById(int id, String teacher) { classRepo.changeClassById(id, teacher); }
    public boolean exists(java.lang.Class<?> c, Object teacher) { return classRepo.exists(c, teacher); }
    public List<Students> getAllStudents(String teacher) { return classRepo.getAllStudents(teacher); }
    public void addStudent(String teacher, String username) { classRepo.addStudent(teacher, username); }
}
