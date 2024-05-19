package edu.comillas.icai.gitt.pat.spring.pf.Controller;


import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloResponse;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
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

//Esta clase tiene el fin de comprobar la correcta funcionalidad de los métodos de cada endpoint en el Controller de la aplicación
@WebMvcTest(ControladorPhotography.class) //emplearemos @WebMvcTest y MockMvc para probar los componentes que implementan un endpoint REST sin necesidad de levantar el servidor
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

     // Empleamos @MockBean para probar el Controller de manera aislada sin necesidad de configurar toda la infraestructura relacionada con el Service (bbdd, dependencias, etc)
     @MockBean private ServicePedido servicePedido;
     @MockBean private ServiceUsuario serviceUsuario;


    //modificar pedido : te envian un articulo request, y lo eliminas , te devuelve el articulo eliminado
    //getPedido pendiente, te dan un token , y devulves un set de articulos o error (notfound) si no hay artuculos en el pedido pendiente
    // addArticulo , recibe un articulo Resquest y se lo pasas a pedidoService, devuelve el articulo que quieres añadir
    //articuloRequest, token, tamaño , cantidad, url
    // ArticuloResponse: url, cantidad, tamaño
    private static final String URL = "src/main/resources/assets/images/Camara-logo.png";
    private static final Long CANTIDAD = 3L;
    private static final Size SIZE = Size.MEDIUM;


    //Este test addArticuloOk() va a verificar que cuando se accede al endpoint "/paulaphotography/pedido/cesta"
    // con el método POST, el articulo pasado en el body de la petición se añade correctamente
    @Test
    void addArticuloOk() throws Exception {

        //Given...
        Mockito.when(servicePedido.eliminarArticulo(Mockito.any(ArticuloRequest.class)))
                .thenReturn( new ArticuloResponse(URL, SIZE, CANTIDAD) );

        String request = "{" +
                "\"url\":\"" + URL + "\"," +
                "\"size\":\"" + SIZE + "\"," +
                "\"cantidad\":\"" + CANTIDAD + "\"}";
        //When...
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/paulaphotography/pedido/cesta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
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
        Mockito.when(servicePedido.eliminarArticulo(Mockito.any(ArticuloRequest.class)))
                .thenReturn( new ArticuloResponse(URL, SIZE, CANTIDAD) );

        String request = "{" +
                "\"url\":\"" + URL + "\"," +
                "\"size\":\"" + SIZE + "\"," +
                "\"cantidad\":\"" + CANTIDAD + "\"}";
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



}
