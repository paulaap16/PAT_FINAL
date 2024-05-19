package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) public Long id;
    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne@JoinColumn(name="pedido_id", referencedColumnName = "id", nullable = false) public Pedido pedido;
    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToMany@JoinColumn(name="foto_id", referencedColumnName = "id", nullable = false) public Foto foto;  //muchas fotos en un solo pedido

    @Enumerated(EnumType.STRING)@Column(nullable=false) public Size size;

    @Column(nullable = false) public Long precioSize;
    @Column(nullable = false) public Long cantidad;


    public void setSize(Size size) {
        this.size = size;
    }

    public void setFoto(Foto foto) {
        this.foto=foto;
    }


    public void setPrecioSize(Long precioSize) {
        this.precioSize=precioSize;
    }

    public Pedido getPedido(){
        return this.pedido;
    }

    public Long getPrecio(){
        return this.precioSize;
    }
}
