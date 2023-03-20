package com.loginregister.user;

import com.loginregister.role.RoleModel;
import com.loginregister.role.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private RoleService roleService;

    private final String NOT_FOUND_BODY = "UserModel not found.";
    private final String DELETED_BODY = "UserModel successfully deleted.";

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){
        var userModel = new UserModel();
        userModel.setUsername(userDto.username());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
        Optional<UserModel> parkingSpotModelOptional = userService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        userService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(DELETED_BODY);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                                           @RequestBody @Valid UserDto userDto){
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        var userModel = new UserModel();
        userModel.setId(userModelOptional.get().getId());
        userModel.setUsername(userDto.username());
        userModel.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @PutMapping("/{userId}/role/{roleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> assignRoleToUser(@PathVariable(value = "userId") UUID userId,
                                                   @PathVariable(value = "roleId") UUID roleId){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        Optional<RoleModel> roleModelOptional = roleService.findById(roleId);

        if(userModelOptional.isEmpty() && roleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User and role not found.");
        }

        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if(roleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }

        var userModel = userModelOptional.get();
        var roleModel = roleModelOptional.get();

        userModel.addRole(roleModel);
        roleModel.addUser(userModel);

        userService.save(userModel);
        roleService.save(roleModel);

        return ResponseEntity.status(HttpStatus.OK).body(
                "User with id: "
                        + userModel.getId() +
                        " is now assigned to role with id: "
                        + roleModel.getId());
    }
}