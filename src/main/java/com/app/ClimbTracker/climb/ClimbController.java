package com.app.ClimbTracker.climb;

import com.app.ClimbTracker.user.AppUser;
import com.app.ClimbTracker.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/climbs")
public class ClimbController {
    private final ClimbRepository climbRepository;
    private final UserRepository userRepository;

    public ClimbController(ClimbRepository climbRepository, UserRepository userRepository) {
        this.climbRepository = climbRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Climb> getMyClimbs(Principal principal) {
        System.out.println("Fetching climbs for user: " + principal.getName());

        return climbRepository.findByUserUsername(principal.getName());
    }

    @PostMapping
    public ResponseEntity<Climb> addClimb(@Valid @RequestBody Climb climb, Principal principal){
        AppUser currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException(("User not found")));
        climb.setUser(currentUser);

        return new ResponseEntity<>(climbRepository.save(climb), HttpStatus.CREATED);
    }

    @GetMapping("/grade/{grade}")
    public List<Climb> getClimbsByGrade(@PathVariable String grade, Principal principal){
        return climbRepository.findByGradeAndUserUsername(grade, principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Climb> getClimbById(@PathVariable Long id, Principal principal){
        java.util.Optional<Climb> climb = climbRepository.findByIdAndUserUsername(id, principal.getName());

        if(climb.isPresent()){
            return new ResponseEntity<>(climb.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClimb(@PathVariable Long id, Principal principal){
        Optional<Climb> climb = climbRepository.findByIdAndUserUsername(id, principal.getName());
        if(climb.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        climbRepository.delete(climb.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Climb> updateClimb(@PathVariable Long id, @Valid @RequestBody Climb climbDetails, Principal principal) {

        java.util.Optional<Climb> optionalClimb = climbRepository.findByIdAndUserUsername(id, principal.getName());

        if (optionalClimb.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Climb existingClimb = optionalClimb.get();

        existingClimb.setGrade(climbDetails.getGrade());
        existingClimb.setStyle(climbDetails.getStyle());
        existingClimb.setCompleted(climbDetails.getCompleted());
        existingClimb.setDate(climbDetails.getDate());

        Climb updatedClimb = climbRepository.save(existingClimb);

        return new ResponseEntity<>(updatedClimb, HttpStatus.OK);
    }
}
