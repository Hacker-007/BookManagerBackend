package com.revan.dao;

import com.revan.model.Class;
import com.revan.model.Role;
import com.revan.model.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClassRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Student student;

    public Session getSession() { return sessionFactory.openSession(); }

    public void createClass(String teacher) {
        Class aClass = new Class();
        aClass.setTeacher(teacher);
        List<Role> defaultRole = new ArrayList<>();
        Session session = getSession();
        defaultRole.add((Role) session.createCriteria(Role.class).add(Restrictions.eq("id", 3)).uniqueResult());
        getSession().save(aClass);
        session.close();
    }

    public void addStudent(String teacher, String username) {
        Session session = getSession();
        Class aClass = (Class) session.createCriteria(Class.class).add(Restrictions.eq("teacher", teacher)).list().get(0);
        List<Students> students = aClass.getStudents();
        Optional<Students> optional = student.findByUsername(username);
        if(optional.isPresent()) {
            Students newStudent = optional.get();
            students.add(newStudent);
            Transaction tx = session.beginTransaction();
            session.update(aClass);
            tx.commit();
        }
        session.close();
    }

    public List<Class> getAllClasses() {
        Session session = getSession();
        List<Class> classes = (List<Class>) session.createCriteria(Class.class).list();
        session.close();
        return classes;
    }

    public Class getClassById(int id) {
        Session session = getSession();
        Class classes = (Class) session.createCriteria(Class.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.close();
        return classes;
    }

    public void deleteClassById(int id) {
        Session session = getSession();
        Class aClass = (Class) session.createCriteria(Class.class).add(Restrictions.eq("id", id)).list().get(0);
        Transaction tx = session.beginTransaction();
        session.delete(aClass);
        tx.commit();
        session.close();
    }

    public void changeClassById(int id, String teacher) {
        Session session = getSession();
        Class aClass = session.get(Class.class, id);
        aClass.setTeacher(teacher);
        session.close();
    }

    public boolean exists(java.lang.Class<?> c, Object teacher){
        Session session = getSession();
        boolean exists = session.createCriteria(c)
                .add(Restrictions.eq("teacher", teacher))
                .uniqueResult() != null;
        session.close();
        return exists;
    }

    public List<Students> getAllStudents(String teacher){
        Session session = getSession();
        Class aClass = (Class) session.createCriteria(Class.class).add(Restrictions.eq("teacher", teacher)).list().get(0);
        session.close();
        return aClass.getStudents();
    }
}
