package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

public class ServiceUsuario {
    @Autowired
    private UsuarioRepository repoUsuario;
    @Autowired
    private FotoRepository repoFoto;
    @Autowired
    private PedidoRepository repoPedido;

    @Autowired
    private TokenRepository repoToken;

    public Usuario authentication(String tokenId) {

        Usuario user = repoToken.findByToken(tokenId);
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no asociado a ningun usuario");
        }
        return user;
    }

    public Token login(String email, String password) {
        Usuario usuario = repoUsuario.findByEmail(email);
        Token tokenUser=null;
        if (usuario != null  && usuario.password.equals(password)) {
            tokenUser = repoToken.findByUsuario(usuario);
            return tokenUser;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Datos incorrectos");
        }


    }

    public ProfileResponse register (RegisterRequest registro) {
        Usuario user = repoUsuario.findByEmailAndName(registro.email(), registro.name());
        if(user!=null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ya existente");
        }
        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setName(registro.name());
        usuarioNuevo.setPassword(registro.password());
        usuarioNuevo.setRole(registro.role());
        usuarioNuevo.setEmail(registro.email());
        repoUsuario.save(usuarioNuevo);
        Token token = new Token ();
        token.setUsuario(usuarioNuevo);
        ProfileResponse profile = new ProfileResponse(usuarioNuevo.name, usuarioNuevo.email, Role.USER);
        return profile;
    }

    public ProfileResponse update(Usuario user, ProfileRequest profile) { //actualizar el perfil de un usuario autenticado
        Usuario usuario = repoUsuario.findByEmailAndName(user.email, user.name);
        if(usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario incorrecto");
        }
        user.setName(profile.getName());
        user.setRole(profile.getRole());
        user.setPassword(profile.getPassword());
        repoUsuario.save(user);

        return new ProfileResponse(user.name, user.email, Role.USER);
    }

    public void logout(String tokenId) {
        Usuario usuario = repoToken.findByToken(tokenId);
        if(usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales no validas");
        }
        Token token = repoToken.findByUsuario(usuario);  //todos los usuarios tienen un token asignado
        repoToken.delete(token);
    }


    public ProfileResponse showUsuario (Long userId) {
        Usuario user = repoUsuario.findById(userId).orElse(null);
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario no registrado");
        }
        return new ProfileResponse(user.name, user.email, Role.USER);
    }
    public void darDeBaja(Long userId) {
        Usuario user = repoUsuario.findById(userId).orElse(null);
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario no registrado");
        }
        repoUsuario.delete(user);
    }


}
