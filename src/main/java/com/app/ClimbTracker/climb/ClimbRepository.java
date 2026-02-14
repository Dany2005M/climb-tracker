package com.app.ClimbTracker.climb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClimbRepository extends JpaRepository<Climb, Long> {
    List<Climb> findByGrade(String grade);
    List<Climb> findByUserUsername(String username);
    Optional<Climb> findByIdAndUserUsername(Long id, String username);
    List<Climb> findByGradeAndUserUsername(String grade, String username);
}
