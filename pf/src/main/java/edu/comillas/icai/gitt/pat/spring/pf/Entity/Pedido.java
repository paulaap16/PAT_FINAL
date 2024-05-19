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
    @Temporal(TemporalType.TIMESTAMP)
    public Date fecha;

    @Column(nullable = false) public Long precioTotal;
    @Column() public String direccion;
    @Column(nullable = false) public String telefono;
    @Column (nullable = false) public String tarjeta;
    @Column(nullable = false) public String fechaVencimientoTarjeta;
    @Column(nullable = false) public String cvv;



    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }



    public void setFecha(Date fecha) {
        this.fecha=fecha;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public Long getPrecioTotal() {
        return this.precioTotal;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario=usuario;
    }

    public void cambiarPrecioTotal(Long precio) {
        this.precioTotal = precio;
    }


    public void setPrecioTotal(Long precioSize) {
        this.precioTotal=this.precioTotal+precioSize;
    }
}
