package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.TokenPedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TokenPedidoRepository extends CrudRepository<TokenPedido, Long> {
    @Query("SELECT t.usuario FROM Token t where t.id=: token_id")
    Pedido findByToken (String token_id);

    Token findById(String id);
    Token findByUsuario( Usuario usuario);
}