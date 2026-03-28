package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<User> allUsers = userService.getAll();
        if (allUsers == null || allUsers.isEmpty()) {
            response.put("recordFound", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("recordFound", true);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.createUser(user);
            response.put("recordCreated", true);
            response.put("record", user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("recordCreated", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable ObjectId id) {
        Map<String, Object> response = new HashMap<>();
        User userInDB = userService.findById(id);
        if (userInDB != null) {
            try{
                userInDB.setUserName(user.getUserName());
                userInDB.setPassword(user.getPassword());
                userService.createUser(userInDB);
                response.put("recordUpdated", true);
                response.put("updatedRecord", userInDB);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception e) {
                response.put("recordUpdated", false);
                response.put("errorMessage", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        response.put("recordFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
