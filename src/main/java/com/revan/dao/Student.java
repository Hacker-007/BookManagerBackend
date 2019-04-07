package com.revan.dao;

import com.revan.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Student extends JpaRepository<Students, Integer> {
    Optional<Students> findByUsername(String username);
}
