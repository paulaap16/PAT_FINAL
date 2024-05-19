package edu.comillas.icai.gitt.pat.spring.pf.Controller;


import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import edu.comillas.icai.gitt.pat.spring.pf.Entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;


import edu.comillas.icai.gitt.pat.spring.pf.model.*;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Esta clase tiene el fin de comprobar la correcta funcionalidad de los métodos de cada endpoint en el Controller de la aplicación
@WebMvcTest(ControladorPhotography.class) //emplearemos @WebMvcTest y MockMvc para probar los componentes que implementan un endpoint REST sin necesidad de levantar el servidor
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

     // Empleamos @MockBean para probar el Controller de manera aislada sin necesidad de configurar toda la infraestructura relacionada con el Service (bbdd, dependencias, etc)
     @MockBean private ServicePedido servicePedido;
     @MockBean private ServiceUsuario serviceUsuario;

    private static final String URL = "src/main/resources/assets/images/Camara-logo.png";
    private static final Long CANTIDAD = 3L;
    private static final Size SIZE = Size.MEDIUM;


    /*Este test addArticuloOk() va a verificar que cuando se accede al endpoint "/paulaphotography/pedido/cesta"
     con el método POST, el articulo pasado en el body de la petición se añade correctamente*/
    @Test
    void addArticuloOk() throws Exception {

        //Given...
        Usuario usuario = new Usuario();
        usuario.email= "nombre@email.com";
        usuario.password = "aaaaaaA1";
        usuario.name = "Nombre";
        usuario.role = Role.USER;
        Token token = new Token();
        token.usuario=usuario;
        Mockito.when(servicePedido.addArticulo(Mockito.eq(token.id), Mockito.any(ArticuloRequest.class)))
                .thenReturn( new ArticuloResponse(URL, SIZE, CANTIDAD) );

        String request = "{" +

                "\"cantidad\":\"" + CANTIDAD + "\"," +
                "\"size\":\"" + SIZE + "\"," +
                "\"url\":\"" + URL + "\"}";
        String sessionCookie = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTYiLCJleHAiOjE2ODQzMjUyMDB9.LCe95pPeMGJ8b4O8XUQ1vN8mYyZkjnCAPk9rOLTNr3c";
        //When...
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/paulaphotography/pedido/cesta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .cookie(new Cookie("session", sessionCookie)))
                //Then...
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("{" +
                        "\"url\":\"" + URL + "\"," +
                        "\"size\":\"" + SIZE + "\"," +
                        "\"cantidad\":\"" + CANTIDAD + "\"}"));

    }

    //Este test va a verificar que si se desea eliminar un articulo de la cesta de la compra (pedido), se realiza correctamente
    @Test
    void modificarPedidoOk() throws Exception{

        //Given...
        Usuario usuario = new Usuario();
        usuario.email= "nombre@email.com";
        usuario.password = "aaaaaaA1";
        usuario.name = "Nombre";
        usuario.role = Role.USER;

        Mockito.when(servicePedido.eliminarArticulo(Mockito.any(ArticuloRequest.class),Mockito.eq(usuario)))
                .thenReturn( new ArticuloResponse(URL, SIZE, CANTIDAD) );

        String request = "{" +
                "\"size\":\"" + SIZE + "\"," +
                "\"cantidad\":\"" + CANTIDAD + "\"," +
                "\"url\":\"" + URL + "\"}";
        //When...
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/paulaphotography/pedido/eliminarArticulo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))

                //Then...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{" +
                        "\"url\":\"" + URL + "\"," +
                        "\"size\":\"" + SIZE + "\"," +
                        "\"cantidad\":\"" + CANTIDAD + "\"}"));

    }

    @Test void registerOk() throws Exception {
        // Given ...
        Mockito.when(serviceUsuario.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(new ProfileResponse("Nombre", "email@email.com", Role.USER));
        String request = "{" +
                "\"name\":\"" + "Nombre" + "\"," +
                "\"email\":\"" + "email@email.com" + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"aaaaaaA1\"," +
                "\"passwordValidate\":\"aaaaaaA1\"}";
        // When ...
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/paulaphotography/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("{" +
                        "\"name\":\"" + "Nombre" + "\"," +
                        "\"email\":\"" + "email@email.com" + "\"," +
                        "\"role\":\"" + Role.USER + "\"}"));
    }

    @Test
    void registerInvalidPassword() throws Exception {
        // Given
        Mockito.when(serviceUsuario.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(new ProfileResponse("Nombre", "email@email.com", Role.USER));
        String request = "{" +
                "\"name\":\"" + "Nombre" + "\"," +
                "\"email\":\"" + "email@email.com" + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"1234\"}";

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/paulaphotography/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
        // Then
        .andExpect(status().isBadRequest());


    }

    //modificar pedido : te envian un articulo request, y lo eliminas , te devuelve el articulo eliminado
    //getPedido pendiente, te dan un token , y devulves un set de articulos o error (notfound) si no hay artuculos en el pedido pendiente
    // addArticulo , recibe un articulo Resquest y se lo pasas a pedidoService, devuelve el articulo que quieres añadir
    //articuloRequest, token, tamaño , cantidad, url
    // ArticuloResponse: cantidad , tamaño, url


}



