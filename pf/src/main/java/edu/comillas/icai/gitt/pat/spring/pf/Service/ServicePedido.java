package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.PedidoRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;

public class ServicePedido {
    @Autowired
    private UsuarioRepository repoUsuario;
    @Autowired
    private FotoRepository repoFoto;
    @Autowired
    private PedidoRepository repoPedido;
    @Autowired
    private HistorialRepository repoHistorial;
    @Autowired
    private TokenPedidoRepository repoTokenPedido;

    public Pedido crear (PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(pedidoRequest.usuario());
        pedido.setSize(pedidoRequest.size());
        pedido.setFoto(pedidoRequest.fotos());
        pedido.setFecha(new Date());
        pedido.setDireccion(pedidoRequest.direccion());
        Long precio= 0L;
        for(Foto foto:pedidoRequest.fotos()) {
            precio+=precio+foto.precio;
        }
        pedido.setPrecio(precio);
        repoPedido.save(pedido);
        return pedido;
    }
    public Pedido authentication(String tokenId) {

        Pedido pedido = repoTokenPedido.findByToken(tokenId);
        if(pedido==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no asociado a ningun usuario");
        }
        return pedido;
    }
    public Pedido modificarPedido (Pedido pedido, PedidoRequest pedidoNuevo) {
        pedido.setDireccion(pedidoNuevo.direccion());
        pedido.setFoto(pedidoNuevo.fotos());
        pedido.setSize(pedidoNuevo.size());
        repoPedido.save(pedido);

        return pedido;
    }

    





}
