package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByEmailAndName(String email, String name);

    Usuario findByEmail(String email);
    Usuario findByName(String name);



}
