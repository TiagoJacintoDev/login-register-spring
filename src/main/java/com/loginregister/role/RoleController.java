package com.loginregister.role;

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
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    private final String NOT_FOUND_BODY = "Role not found.";
    private final String DELETED_BODY = "Role successfully deleted.";

    @PostMapping
    public ResponseEntity<Object> saveRole(@RequestBody @Valid RoleDto roleDto){
        var roleModel = new Role();
        BeanUtils.copyProperties(roleDto, roleModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleModel));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneRole(@PathVariable(value = "id") UUID id){
        Optional<Role> roleModelOptional = roleService.findById(id);
        if (roleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(roleModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable(value = "id") UUID id){
        Optional<Role> parkingSpotModelOptional = roleService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        roleService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(DELETED_BODY);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable(value = "id") UUID id,
                                                           @RequestBody @Valid RoleDto roleDto){
        Optional<Role> roleModelOptional = roleService.findById(id);
        if (roleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_BODY);
        }
        var roleModel = new Role();
        BeanUtils.copyProperties(roleDto, roleModel);
        roleModel.setId(roleModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(roleService.save(roleModel));
    }
}