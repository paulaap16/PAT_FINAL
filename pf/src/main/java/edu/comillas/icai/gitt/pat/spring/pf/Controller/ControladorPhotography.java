package edu.comillas.icai.gitt.pat.spring.pf.Controller;


import edu.comillas.icai.gitt.pat.spring.pf.Entity.Articulo;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.LoginRequest;

import edu.comillas.icai.gitt.pat.spring.pf.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
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
    public Usuario register(@Valid @RequestBody RegisterRequest register) {  //@RequestBody en Spring se utiliza para indicar que el parámetro de un método de controlador debe
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



    @GetMapping("/paulaphotography/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario profile(@CookieValue(value = "session", required = true) String session) {
        Usuario user = userService.authentication(session);
        if (user == null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return userService.showUsuario(user.id);
    }

    @PutMapping("/paulaphotography/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario update(@RequestBody ProfileRequest profile, @CookieValue(value = "session", required = true) String session) {
        Usuario user = userService.authentication(session);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return userService.update(user, profile);

    }

    @GetMapping("/paulaphotography/user/{id}/pedidoPendiente")
    @ResponseStatus(HttpStatus.OK)
    public Set<Articulo> getPedidoPendiente (@PathVariable Token token) {
        Usuario user = token.getUsuario();
        return pedidoService.pedidoPendiente(user);
    }

    @DeleteMapping("/paulaphotography/user/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void darDeBaja (@PathVariable Long userId) {
        userService.darDeBaja(userId);
    }


    //PEDIDOS

    @PostMapping("/paulaphotography/pedido/cesta")
    @ResponseStatus(HttpStatus.CREATED)
    public Articulo addArticulo(@CookieValue(value = "session", required = true) String session, @Valid @RequestBody ArticuloRequest articuloNuevo) {
        //Verifico si existe la foto.
        return pedidoService.addArticulo(session, articuloNuevo);
    }

    @PutMapping("/paulaphotography/pedido/eliminarArticulo")
    @ResponseStatus(HttpStatus.OK)
    public Articulo modificarPedido(@Valid @RequestBody ArticuloRequest pedidoEliminado, @CookieValue(value = "session", required = true) String session) {
        return pedidoService.eliminarArticulo(pedidoEliminado);
    }

    @PutMapping("/paulaphotography/pedido/cesta/fin")
    @ResponseStatus(HttpStatus.OK)
    public void finCompra(@Valid @RequestBody Token tokenUsuario) {
        //Verifico si existe la foto.
        pedidoService.finCompra(tokenUsuario.usuario);
    }
}
