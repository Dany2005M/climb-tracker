package com.app.ClimbTracker.climb;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/climbs")
public class ClimbController {
    private final ClimbRepository climbRepository;

    public ClimbController(ClimbRepository climbRepository) {
        this.climbRepository = climbRepository;
    }

    @GetMapping
    public List<Climb> getAllClimbs() {
        return climbRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Climb> createClimb(@Valid @RequestBody Climb climb){
        Climb newClimb = climbRepository.save(climb);

        return new ResponseEntity<>(newClimb, HttpStatus.CREATED);
    }

    @GetMapping("/grade/{grade}")
    public List<Climb> getClimbsByGrade(@PathVariable String grade){
        return climbRepository.findByGrade(grade);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Climb> getClimbById(@PathVariable Long id){
        java.util.Optional<Climb> climb = climbRepository.findById(id);

        if(climb.isPresent()){
            return new ResponseEntity<>(climb.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClimb(@PathVariable Long id){
        if(!climbRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        climbRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Climb> updateClimb(@PathVariable Long id, @Valid @RequestBody Climb climbDetails) {

        java.util.Optional<Climb> optionalClimb = climbRepository.findById(id);

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
