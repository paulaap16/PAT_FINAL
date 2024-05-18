package edu.comillas.icai.gitt.pat.spring.pf.model;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record PedidoRequest(
        Usuario usuario,
        Size size,
        Set<Foto> fotos,
        String direccion
) {

}
