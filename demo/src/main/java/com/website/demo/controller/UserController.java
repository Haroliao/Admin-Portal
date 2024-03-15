package com.website.demo.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.demo.entity.User;
import com.website.demo.repository.UserRepository;
//controller layer

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/addOrUpdateUser")
    //@RequestBody convert the body of the HTTP request to a Java object, typically deserialize JSON into Java objects.
    //@RequestParam is used to extract parameters from the request body (when content type is application/x-www-form-urlencoded).
    public ResponseEntity<String> addOrUpdateUser(@RequestBody User user, @RequestParam String operationType) {
    	 System.out.println("Received user: " + user + ", operationType: " + operationType);
    	 //null and unfilled space is different, both case need to be considered
    	 if (user.getUsername() == null || user.getUsername().trim().isEmpty() || 
    			    user.getName() == null || user.getName().trim().isEmpty() || 
    			    user.getPhone() == null || user.getPhone().trim().isEmpty() || 
    			    user.getLocation() == null || user.getLocation().trim().isEmpty() || 
    			    user.getRegistrationDate() == null || // Assuming RegistrationDate is a Date object and cannot be an empty string
    			    user.getOrganization() == null || user.getOrganization().trim().isEmpty() || 
    			    user.getRole() == null || user.getRole().trim().isEmpty()) {
    			    return ResponseEntity.badRequest().body("Error: Information incomplete.");
    			}


        if ("insert".equals(operationType)) {
            // Check if the user already exists for 'insert' operation
            if (userRepository.findById(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Error: Username already exists.");
            }
            // Insert the new user
            userRepository.save(user);
            return ResponseEntity.ok("User added successfully.");
        }else if ("update".equals(operationType)) {
            // For 'update' operation, check if the user exists
            Optional<User> existingUser = userRepository.findById(user.getUsername());
            // Update the existing user's information
            // Here, you should only update the fields that are allowed to be updated
            User updateUser = existingUser.get();
            updateUser.setName(user.getName());
            updateUser.setPhone(user.getPhone());
            updateUser.setLocation(user.getLocation());
            updateUser.setRegistrationDate(user.getRegistrationDate());
            updateUser.setOrganization(user.getOrganization());
            updateUser.setRole(user.getRole());

            userRepository.save(updateUser); // Save the updated user
            return ResponseEntity.ok("User updated successfully.");
        } else {
            // Handling invalid operation types
            return ResponseEntity.badRequest().body("Error: Invalid operation type.");
        }
    }

    @GetMapping("/displayUsers")
    public List<User> fetchUsers() {
        return userRepository.findAll();
    }
    
    @DeleteMapping("/{username}")
    //@PathVariable is used to handle dynamic URL, considered different username 
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Error: User not found.");
        }
    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDataToServer(@RequestBody List<User> users) {
        for (User user : users) {
        	if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"Username must not be empty.\"}");
            }
        	 String formattedDate = convertDate(user.getRegistrationDate());
             if (formattedDate == null) {
                 // Handle invalid date format
                 return ResponseEntity.badRequest().body("{\"error\": \"Invalid date format for user: " + user.getUsername() + "\"}");
             }
             user.setRegistrationDate(formattedDate);
            User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);

            if (existingUser != null) {
                // Update existing user
                updateExistingUser(existingUser, user);
                userRepository.save(existingUser);
            } else {
           
                // Save new user
                userRepository.save(user);
            }
        }
        return ResponseEntity.ok("{\"message\": \"Users processed successfully.\"}");
    }

    private void updateExistingUser(User existingUser, User user) {
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        existingUser.setLocation(user.getLocation());
        existingUser.setRegistrationDate(user.getRegistrationDate());
        existingUser.setOrganization(user.getOrganization());
        existingUser.setRole(user.getRole());
    }
  //mysql DATE type expect yyyy-MM-dd, conversion needed
    private String convertDate(String registrationDate) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(registrationDate, inputFormatter);
            return outputFormatter.format(date);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null; 
        }
    }



    
}
