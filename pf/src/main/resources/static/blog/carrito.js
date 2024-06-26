function pedido(datosJsonFormulario) {
  //alert('Inicio de pedido');
  //alert(datosJsonFormulario);
  fetch('/paulaphotography/pedido/cesta', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}, credentials: 'include'})
    .then(response => {
      if (response.ok) alert('Foto añadida al carrito'); //mostrarAviso2
      else alert('✖︎ Error al añadir el elemento al pedido');
    });
    alert('Fin de pedido');
}

function mostrarAviso2(texto) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  //aviso.className = tipo;
}

function form2json(idForm) {
    const formulario=document.getElementById(idForm);
    const formData = new FormData(formulario);
    const orden = {
           cantidad: formData.get('cantidad'),
           size: formData.get('tamaño'),
           url: formData.get('imagen'),
       };
    const jsonAEnviar=JSON.stringify(orden);  //construimos un JSON con los datos del form
    //alert(jsonAEnviar);
    return jsonAEnviar;
}
function accederCarrito() {
    fetch('/paulaphotography/user/pedidoPendiente', {
        method: 'get',
        headers: {'content-type': 'application/json'},
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        if (data.length > 0) {
            generarTablaCarrito(data);
        } else {
            mostrarAviso('No hay pedidos pendientes', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        mostrarAviso('Error al obtener los datos del carrito', 'error');
    });
}

function generarTablaCarrito(articulos) {
    const tabla = document.createElement('table');
    tabla.classList.add('tabla-carrito');

    const cabecera = document.createElement('thead');
    cabecera.innerHTML = `
<tr>
<th>Foto</th>
<th>Tamaño</th>
<th>Cantidad</th>
<th>Acciones</th>
</tr>
    `;
    tabla.appendChild(cabecera);

    const cuerpo = document.createElement('tbody');
    articulos.forEach((articulo, index) => {
        const fila = document.createElement('tr');
        fila.innerHTML = `
<td><img src="${articulo.foto}" alt="Artículo" class="foto-articulo"></td>
<td>${articulo.tamaño}</td>
<td>${articulo.cantidad}</td>
<td><button onclick="eliminarArticulo(${articulo})">Eliminar</button></td>
        `;
        cuerpo.appendChild(fila);
    });
    tabla.appendChild(cuerpo);

    const contenedor = document.getElementById('lista-compra-contenedor');
    contenedor.innerHTML = ''; // Limpiar contenido anterior
    contenedor.appendChild(tabla);
}

function eliminarArticulo(articulo) {
    fetch('/paulaphotography/pedido/eliminarArticulo', {
        method: 'put',
        body: articulo,
        headers: {'content-type': 'application/json'},
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            accederCarrito(); // Recargar la tabla después de eliminar el artículo
        } else {
            mostrarAviso('Error al eliminar el artículo', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        mostrarAviso('Error al eliminar el artículo', 'error');
    });
}

function mostrarAviso(mensaje, tipo) {
    const aviso = document.createElement('div');
    aviso.className = `aviso ${tipo}`;
    aviso.textContent = mensaje;
    document.body.appendChild(aviso);

    setTimeout(() => {
        aviso.remove();
    }, 3000);
}

//  FINALIZAR O ELIMINAR PEDIDO

function form2json2(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

function mostrarAviso3(texto) {
  const aviso = document.getElementById("avisos");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function cancelarCompra() {
  fetch('/paulaphotography/pedido/cesta/fin', {
  method: 'post',
  headers: {'content-type': 'application/json'},
  credentials: 'include'
  })
    .then(response => {
      if (response.ok) {
        document.getElementById('lista-compra-contenedor').innerHTML = '';
        mostrarAviso3("se ha eliminado todo el carrito");
      }
      else mostrarAviso('✖︎ Error en el registro. Compruebe que los datos están bien introducidos', 'error');
    });
}

function finalizarCompra() {
  fetch('/paulaphotography/pedido/cesta/fin', {method: 'post', headers: {'content-type': 'application/json'}, credentials: 'include'})
    .then(response => {
      if (response.ok) {
        document.getElementById('lista-compra-contenedor').innerHTML = '';
        location.href = 'pagar.html';
      }
      else mostrarAviso('✖︎ Error en el registro. Compruebe que los datos están bien introducidos', 'error');
    });
}
/*
let carrito = {}; // Objeto para almacenar las imágenes seleccionadas y su cantidad

function añadirAlCarrito(boton) {
    const imagen = boton.parentElement.parentElement.querySelector('img').src;
    
    if (carrito[imagen]) {
        carrito[imagen]++;
    } else {
        carrito[imagen] = 1;
    }

    actualizarContador();
}
function nada(nada){
const nada = 0;
}
function actualizarContador() {
    const cartCounter = document.getElementById('cart-counter');
    const totalItems = Object.values(carrito).reduce((acc, cur) => acc + cur, 0);
    cartCounter.textContent = totalItems;
    cartCounter.style.display = 'block';
}
*/
/*
document.querySelector('.logo-container').addEventListener('click', function() {
    const cartCounter = parseInt(document.getElementById('cart-counter').textContent);
    //window.location.href = 'carrito.html';
    if (cartCounter !== 0) {
        localStorage.setItem('carrito', JSON.stringify(carrito));
        window.location.href = 'carrito2.html'; // Cambiar por la ruta correcta de la página del carrito de la compra
    }
});
*/