package edu.comillas.icai.gitt.pat.spring.pf.Service;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Articulo;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Foto;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Pedido;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Repository.*;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.PedidoRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
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
    private HistorialRepository repoHistorial;
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

    public void finCompra(Usuario user) {
        Pedido pedidoUsuario = repoPedido.findByUsuarioAndFecha(user, null);
        if (pedidoUsuario == null) {
            pedidoUsuario.setFecha(new Date());
            repoPedido.save(pedidoUsuario); // Asegúrate de guardar los cambios
        }
    }

    





}
