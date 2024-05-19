package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.*;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public Articulo addArticulo (ArticuloRequest articuloRequest) {
        //verifico que existe la foto asociada a la url
        Foto foto = repoFoto.findByUrl(articuloRequest.url());
        if(foto==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url no asociada a ninguna foto");
        }
        //verifico si ya existe pedido: me depende de si el usuario tiene una fecha vacia o no. si la fecha no esta puesta, se crea el pedido.
        Usuario user =articuloRequest.token().usuario;
        if(user==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
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
        return articulo;
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


    public void finCompra(Usuario user) {
        Pedido pedidoUsuario = repoPedido.findByUsuarioAndFecha(user, null);
        if (pedidoUsuario == null) {
            pedidoUsuario.setFecha(new Date());
            repoPedido.save(pedidoUsuario); // Asegúrate de guardar los cambios
        }
    }

}
