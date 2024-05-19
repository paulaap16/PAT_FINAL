package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import jakarta.persistence.*;
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) public Long id;
    @Column(nullable = false, unique = true) public String name;
    @Column(nullable = false, unique = true) public String email;
    @Column(nullable = false, unique=true) public String password;
    @Enumerated(EnumType.STRING) public Role role;



    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setRole(Role role) {
        this.role=role;
    }

    public void setEmail(String email) {
        this.email=email;
    }
}
