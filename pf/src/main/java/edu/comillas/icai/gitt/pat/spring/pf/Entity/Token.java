package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    public Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}