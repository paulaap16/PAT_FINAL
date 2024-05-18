package edu.comillas.icai.gitt.pat.spring.pf.Entity;

import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import jakarta.persistence.*;
@Entity
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false) public Long precio;

    @Column(nullable = false) public String tematica;
    @Column(nullable = false) public String url;
    @Enumerated(EnumType.STRING) public Size size; //como se puede hacer pedido de varias fotos, cada foto

}