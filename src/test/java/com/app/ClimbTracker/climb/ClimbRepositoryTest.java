package com.app.ClimbTracker.climb;

import com.app.ClimbTracker.user.AppUser;
import com.app.ClimbTracker.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ClimbRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClimbRepository climbRepository;

    @Test
    public void testFindByUserUsername_ReturnsCorrectClimbs() {

        AppUser dummyUser = new AppUser("testClimber", "password123", "USER");
        userRepository.save(dummyUser);

        Climb dummyClimb = new Climb("V5", "Overhang", true, LocalDate.now());
        dummyClimb.setUser(dummyUser);
        climbRepository.save(dummyClimb);

        List<Climb> foundClimbs = climbRepository.findByUserUsername("testClimber");

        assertEquals(1, foundClimbs.size());

        assertEquals("V5", foundClimbs.getFirst().getGrade());
    }
}
