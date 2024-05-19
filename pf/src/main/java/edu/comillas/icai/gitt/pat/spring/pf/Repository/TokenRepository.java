package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    @Query("SELECT t.usuario FROM Token t where t.id=: token_id")
    Usuario findByToken (String token_id);

    Token findById(String id);

    Token findByUsuario( Usuario usuario);
}

