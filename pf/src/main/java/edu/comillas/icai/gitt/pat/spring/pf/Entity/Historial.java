package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)@OneToOne
    @JoinColumn(name="usuario_id", referencedColumnName = "id", nullable = false) public Usuario usuario;
    @ManyToMany
    @JoinTable(
            name = "historial_pedido",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    private Set<Pedido> pedidos = new HashSet<>();


    public void setUsuario(Usuario user) {
        this.usuario=user;
    }
    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
