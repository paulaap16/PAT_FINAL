package edu.comillas.icai.gitt.pat.spring.pf.Repository;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface FotoRepository extends CrudRepository<Foto, Long> {
    Foto findByUrl(String url);
}
