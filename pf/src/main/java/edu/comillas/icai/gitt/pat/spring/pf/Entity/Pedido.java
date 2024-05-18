package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;

import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) public Long id;
    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne@JoinColumn(name="usuario_id", referencedColumnName = "id", nullable = false) public Usuario usuario;
    //@OnDelete(action = OnDeleteAction.CASCADE)@ManyToMany@JoinColumn(name="foto_id", referencedColumnName = "id", nullable = false) public Foto foto;  //muchas fotos en un solo pedido

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(
            name = "pedido_foto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "foto_id")
    )
    public Set<Foto> fotos = new HashSet<>();  //compra de varias fotos a la vez

    @Column(nullable = false) public Size size;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date fecha;
    //@Column(nullable = false) public Boolean completada;
    @Column(nullable = false) public String direccion;
    @Column(nullable = false) public Long precio;

    public void setUsuario(Usuario usuario) {
        this.usuario=usuario;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setFoto(Set<Foto> fotos) {
        this.fotos=fotos;
    }

    public void setFecha(Date fecha) {
        this.fecha=fecha;
    }

    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }

    public void setPrecio(Long precio) {
        this.precio=precio;
    }
}
