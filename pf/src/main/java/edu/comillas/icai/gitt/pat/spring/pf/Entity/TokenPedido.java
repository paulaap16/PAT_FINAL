package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class TokenPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    public Pedido pedido;
}
