package com.loginregister.user;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final String NOT_FOUND_BODY = "User not found.";
    private final String DELETED_BODY = "User successfully deleted.";

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        Optional<User> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
        Optional<User> parkingSpotModelOptional = userService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        userService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(DELETED_BODY);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                                           @RequestBody @Valid UserDto userDto){
        Optional<User> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setId(userModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }
}