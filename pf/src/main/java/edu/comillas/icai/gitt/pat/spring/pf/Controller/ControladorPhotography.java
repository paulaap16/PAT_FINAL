package edu.comillas.icai.gitt.pat.spring.pf.Controller;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Historial;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.PedidoRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Provider;
import java.util.List;

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

    @GetMapping("/paulaphotography/user/{id}/historial")
    @ResponseStatus(HttpStatus.OK)
    public Historial historial (@PathVariable Long userId) {
        return userService.historial(userId);
    }

    @DeleteMapping("/paulaphotography/user/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void darDeBaja (@PathVariable Long userId) {
        userService.darDeBaja(userId);
    }

    @PostMapping("/paulaphotography/pedido/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crear(@Valid @RequestBody PedidoRequest pedidoNuevo) {  //@RequestBody en Spring se utiliza para indicar que el parámetro de un método de controlador debe
        // ser vinculado al cuerpo de la solicitud HTTP.
        try {
            return pedidoService.crear(pedidoNuevo);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PutMapping("/paulaphotography/pedido/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pedido modificarPedido(@Valid @RequestBody PedidoRequest pedidoNuevo, @CookieValue(value = "session", required = true) String session) {
        Pedido pedido = pedidoService.authentication(session);
        if (pedido == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return pedidoService.modificarPedido(pedido, pedidoNuevo);

    }





}
