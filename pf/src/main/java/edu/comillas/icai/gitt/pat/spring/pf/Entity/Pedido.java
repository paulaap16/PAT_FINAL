package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToMany
    @JoinColumn(name="usuario_id", referencedColumnName = "id", nullable = false) public Usuario usuario;  //muchas fotos en un solo pedido
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date fecha;

    @Column(nullable = false) public Long precioTotal;
    @Column() public String direccion;

    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }



    public void setFecha(Date fecha) {
        this.fecha=fecha;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario=usuario;
    }

}
