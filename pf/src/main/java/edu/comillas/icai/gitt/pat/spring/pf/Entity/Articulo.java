package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    public void setCantidad(Long cantidad) {
        this.cantidad=cantidad;
    }
    public void setPedido(Pedido pedido) {
        this.pedido=pedido;
    }
    public void setPrecioSize(Long cantidad, Size size, Long precioFoto) {
        Long extra=0L;
        if(size==Size.MEDIUM) {
            extra=2L;
        } else if (size==Size.LARGE) {
            extra=4L;
        }
        this.precioSize=(precioFoto+extra)*cantidad;
    }
}
