package edu.comillas.icai.gitt.pat.spring.pf.Controller;


import edu.comillas.icai.gitt.pat.spring.pf.Service.ServicePedido;
import edu.comillas.icai.gitt.pat.spring.pf.Service.ServiceUsuario;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    private static final Integer CANTIDAD = 3;
    private static final Size SIZE = Size.MEDIUM;

    @Test
    void modificarPedidoOK() throws Exception{

        //Given...
        Mockito.when(servicePedido.eliminarArticulo(Mockito.any(ArticuloRequest.class)))
                .thenReturn(new List<ArticuloResponse>(URL,CANTIDAD, SIZE ));


    }





}

/*// Given ...
        Mockito.when(userService.profile(Mockito.any(RegisterRequest.class)))
                .thenReturn(new ProfileResponse(NAME, EMAIL, Role.USER));
        String request = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"aaaaaaA1\"}";
        // When ...
        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            // Then ...
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().string("{" +
                    "\"name\":\"" + NAME + "\"," +
                    "\"email\":\"" + EMAIL + "\"," +
                    "\"role\":\"" + Role.USER + "\"}"));*/
