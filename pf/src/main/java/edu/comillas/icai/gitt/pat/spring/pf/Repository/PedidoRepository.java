package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Set;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    Set<Pedido> findByUsuario (Usuario usuario);

    Pedido findByUsuarioAndFecha(Usuario usuario, Date fecha);

}