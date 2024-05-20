package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.*;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServicePedido {
    @Autowired
    private UsuarioRepository repoUsuario;
    @Autowired
    private FotoRepository repoFoto;
    @Autowired
    private PedidoRepository repoPedido;
    @Autowired
    private TokenRepository repoToken;
    @Autowired
    private ArticuloRepository repoArticulo;

    public ArticuloResponse addArticulo (String tokenId, ArticuloRequest articuloRequest) {
        //verifico que existe la foto asociada a la url
        Foto foto = repoFoto.findByUrl(articuloRequest.url());
        if(foto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url no asociada a ninguna foto");
        }
        //verifico si ya existe pedido: me depende de si el usuario tiene una fecha vacia o no. si la fecha no esta puesta, se crea el pedido.
        Usuario user =repoToken.findByToken(tokenId);
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario no encontrado");
        }

        //creo el articulo para guardarlo en la base de datos.
        Articulo articulo = new Articulo();
        articulo.setFoto(foto);
        articulo.setSize(articuloRequest.size());
        articulo.setCantidad(articuloRequest.cantidad());
        articulo.setPrecioSize(articuloRequest.cantidad(), articuloRequest.size(), foto.precio);

        Pedido pedidoUsuario= repoPedido.findByUsuarioAndFecha (user, null);
        if(pedidoUsuario==null){ //es el primer articulo que se añade a la cesta
            Pedido pedido = new Pedido();
            pedido.setFecha(null);
            pedido.setUsuario(user);
            pedido.setPrecioTotal(articulo.precioSize);
            repoPedido.save(pedido);
            articulo.setPedido(pedido);
        }//sino ya hay algun articulo en la cesta: el pedido ya ha sido creado.
        else {
            articulo.setPedido(pedidoUsuario);
        }
        repoArticulo.save(articulo);
        ArticuloResponse articuloResponse = new ArticuloResponse(foto.url, articulo.size,articulo.cantidad);
        return articuloResponse;
    }

    @Transactional
    public ArticuloResponse eliminarArticulo (ArticuloRequest articulo, Usuario usuario) {
        Foto foto = repoFoto.findByUrl(articulo.url());
        Articulo articuloEliminar = repoArticulo.findByCantidadAndSizeAndFoto(usuario, articulo.size(), foto);
        if(articuloEliminar.getPedido().getFecha() != null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Pedido pedido = articuloEliminar.getPedido();
        pedido.cambiarPrecioTotal(pedido.getPrecioTotal()-articuloEliminar.getPrecio());
        if(pedido.getPrecioTotal() == 0L){
            repoPedido.delete(pedido);
        }
        repoArticulo.delete(articuloEliminar);
        ArticuloResponse articuloResponse = new ArticuloResponse(foto.url, articuloEliminar.size, articuloEliminar.cantidad);
        return articuloResponse;
    }

    public Set<ArticuloResponse> pedidoPendiente (Usuario user) {
        Set<ArticuloResponse> articulosResponse = new HashSet<>();
        Set<Pedido> pedidos = repoPedido.findByUsuario(user);
        for (Pedido pedido: pedidos){
            if(pedido.getFecha() == null){
                if(pedido.getPrecioTotal() == 0L) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                Set<Articulo> articulos =  repoArticulo.findByPedido(pedido);
                for(Articulo articulo : articulos){
                    ArticuloResponse articuloResponse = new ArticuloResponse(articulo.foto.getUrl(), articulo.size, articulo.cantidad);
                    articulosResponse.add(articuloResponse);
                }

                return articulosResponse;
            }
        }
        return null;
    }

    public void finCompra(CompradorRequest compradorRequest) {
        Usuario user = repoUsuario.findByEmailAndName(compradorRequest.email(), compradorRequest.name());
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        Pedido pedidoUsuario = repoPedido.findByUsuarioAndFecha(user, null);
        if (pedidoUsuario == null) {
            pedidoUsuario.setFecha(new Date());
            repoPedido.save(pedidoUsuario); // Asegúrate de guardar los cambios
        }
    }

    public void eliminarCompra(String tokenId) {
        //la compra que se va a realizar todavia no ha finalizado definitivamente por lo que la fecha no ha sido asignada. Asi se rescata el pedido que se quiere eliminar.
        Usuario user = repoToken.findByToken(tokenId);
        Pedido pedidoUsuario= repoPedido.findByUsuarioAndFecha (user, null);
        if(pedidoUsuario==null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido no existe");
        }
        repoPedido.delete(pedidoUsuario);

    }

}
