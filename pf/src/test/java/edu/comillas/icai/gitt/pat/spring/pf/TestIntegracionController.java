package edu.comillas.icai.gitt.pat.spring.pf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.UsuarioRepository;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(Controller.class)
public class TestIntegracionController {
    @Autowired
    TokenRepository repoToken;
    @Autowired
    UsuarioRepository repoUsuario;
    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";
    private static final String PASSWORD = "aaaaaaA1";


    @Test
    void registerOk() throws Exception {
        Usuario usuario = new Usuario();

        usuario.name= NAME;
        usuario.email= EMAIL;
        usuario.password= PASSWORD;
        usuario.role=Role.valueOf("USER");

        Token token = new Token();
        token.usuario= usuario;
        repoUsuario.save(usuario);
        repoToken.save(token);

        Usuario userSaved = repoUsuario.findByEmailAndName(usuario.email, usuario.name);
        Token tokenSaved = repoToken.findByUsuario(token.usuario);

        DataIntegrityViolationException error = null;
        try {
            assertNotNull(userSaved);
            assertEquals(usuario.email, userSaved.email);
            assertEquals(usuario.password, userSaved.password);
            assertEquals(usuario.role, userSaved.role);
            assertEquals(usuario.name, userSaved.name);

            assertNotNull(tokenSaved);
            assertEquals(token.usuario.id, tokenSaved.usuario.id);

        } catch (DataIntegrityViolationException e) { error = e; }
        // Then ...
        assertNull(error);


    }
}
