package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Articulo;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;

import java.util.Set;

public interface ArticuloRepository extends CrudRepository<Articulo, Long> {
    Set<Articulo> findByPedido(Pedido pedido);

    Articulo findByUsuarioAndSizeAndFoto(Usuario usuario, Size size, Foto foto);
}
