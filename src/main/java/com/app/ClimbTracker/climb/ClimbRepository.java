package com.app.ClimbTracker.climb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClimbRepository extends JpaRepository<Climb, Long> {
    List<Climb> findByGrade(String grade);
}
