package com.loginregister.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loginregister.user.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "TB_ROLE")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<UserModel> users;

    public void addUser(UserModel user) {
        users.add(user);
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}