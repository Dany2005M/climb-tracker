package com.app.ClimbTracker.user;

import com.app.ClimbTracker.climb.ClimbRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final ClimbRepository climbRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, ClimbRepository climbRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.climbRepository = climbRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser newUser){

        if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setRole("USER");
        userRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser, Principal principal){
        AppUser  existingUser = userRepository.findById(id).orElse(null);
        if(existingUser == null){
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        if(!existingUser.getUsername().equals(principal.getName())) {
            return new ResponseEntity<>("Forbidden: You can only update your own account!", HttpStatus.FORBIDDEN);
        }

        if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(existingUser);
        return new ResponseEntity<>("Account updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, Principal principal){
        AppUser existingUser=  userRepository.findById(id).orElse(null);
        if(existingUser == null){
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        if(!existingUser.getUsername().equals(principal.getName())){
            return new ResponseEntity<>("Forbidden: You can only delete your own account", HttpStatus.FORBIDDEN);
        }

        climbRepository.deleteAll(climbRepository.findByUserUsername(existingUser.getUsername()));
        userRepository.delete(existingUser);
        return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
    }


}
