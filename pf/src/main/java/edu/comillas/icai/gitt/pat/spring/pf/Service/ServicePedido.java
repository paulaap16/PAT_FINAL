package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.*;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.PedidoRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
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
    private ArticuloRepository repoArticulo;

    public Articulo crear (PedidoRequest pedidoRequest) {
        Articulo pedido = new Articulo();
        //pedido.setUsuario(pedidoRequest.usuario());
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
    public Articulo eliminarArticulo (ArticuloRequest articulo) {
        Usuario usuario = articulo.token().getUsuario();
        if(usuario == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Foto foto = repoFoto.findByUrl(articulo.url());
        Articulo articuloEliminar = repoArticulo.findByUsuarioAndSizeAndFoto(usuario, articulo.size(), foto);
        if(articuloEliminar.getPedido().getFecha() != null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        repoArticulo.delete(articuloEliminar);
        return articuloEliminar;
    }

    public Set<Articulo> pedidoPendiente (Usuario user) {
        Set<Pedido> pedidos = repoPedido.findByUsuario(user);
        for (Pedido pedido: pedidos){
            if(pedido.getFecha() == null){
                if(pedido.getPrecioTotal() == 0) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                return repoArticulo.findByPedido(pedido);
            }
        }
        return null;
    }

}
