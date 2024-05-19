package edu.comillas.icai.gitt.pat.spring.pf.Controller;


import edu.comillas.icai.gitt.pat.spring.pf.Entity.Articulo;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

public class ControladorPhotography {
    @Autowired
    private ServiceUsuario userService;

    @Autowired
    private ServicePedido pedidoService;


    //metodos para usuario

    @PostMapping("/paulaphotography/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody RegisterRequest register) {  //@RequestBody en Spring se utiliza para indicar que el parámetro de un método de controlador debe
        // ser vinculado al cuerpo de la solicitud HTTP.
        try {
            return userService.register(register);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

    }

    @PostMapping("/paulaphotography/user/session")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest credentials) {
        Token token = userService.login(credentials.email(), credentials.password());

        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ResponseCookie session = ResponseCookie
                .from("session", token.id)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    @DeleteMapping("/paulaphotography/user/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "session", required = true) String session) {
        Usuario usuario = userService.authentication(session);
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        userService.logout(session);
        ResponseCookie expireSession = ResponseCookie
                .from("session")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, expireSession.toString()).build();
    }

/*

    @GetMapping("/paulaphotography/user/me")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse profile(@CookieValue(value = "session", required = true) String session) {
        Usuario user = userService.authentication(session);
        if (user == null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return userService.showUsuario(user.id);
    }

    @PutMapping("/paulaphotography/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse update(@RequestBody ProfileRequest profile, @CookieValue(value = "session", required = true) String session) {
        Usuario user = userService.authentication(session);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return userService.update(user, profile);

    }

    @DeleteMapping("/paulaphotography/user/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void darDeBaja (@RequestBody ProfileRequest profile) {
        userService.darDeBaja(profile);
    }


 */

    //PEDIDOS

    @GetMapping("/paulaphotography/user/pedidoPendiente")
    @ResponseStatus(HttpStatus.OK)
    public Set<ArticuloResponse> getPedidoPendiente (@CookieValue(value = "session", required = true) String session) {
        Usuario user = userService.authentication(session);
        return pedidoService.pedidoPendiente(user);
    }

    @PostMapping("/paulaphotography/pedido/cesta")
    @ResponseStatus(HttpStatus.CREATED)
    public Articulo addArticulo(@CookieValue(value = "session", required = true) String session, @Valid @RequestBody ArticuloRequest articuloNuevo) {
        //Verifico si existe la foto.
        return pedidoService.addArticulo(session, articuloNuevo);
    }

    @PutMapping("/paulaphotography/pedido/eliminarArticulo")
    @ResponseStatus(HttpStatus.OK)
    public ArticuloResponse modificarPedido(@Valid @RequestBody ArticuloRequest pedidoEliminado, @CookieValue(value = "session", required = true) String session) {
        Usuario usuario = userService.authentication(session);
        if(usuario == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return pedidoService.eliminarArticulo(pedidoEliminado, usuario);
    }

    @PutMapping("/paulaphotography/pedido/cesta/fin")
    @ResponseStatus(HttpStatus.OK)
    public void finCompra(@Valid @RequestBody CompradorRequest compradorRequest) {
        //Verifico si existe la foto.
        pedidoService.finCompra(compradorRequest);
    }

    @DeleteMapping("/paulaphotography/pedido/cesta/borrar")
    @ResponseStatus(HttpStatus.OK)
    public void eliminarCompra(@CookieValue(value = "session", required = true) String session) {
        pedidoService.eliminarCompra(session);
    }

}
