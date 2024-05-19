function pedido(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/paulaphotography/pedido/nuevo', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}})
    .then(response => {
      if (response.created) mostrarAviso('Foto añadida al carrito');
      else mostrarAviso('✖︎ Error al añadir el elemento al pedido', 'error');
    });
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event, boton) {
  const imagen = boton.parentElement.parentElement.querySelector('img').src;
  event.preventDefault();
  const data = new FormData(event.target);
  data.append('imagen', imagen);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

// Verificar si el mensaje de bienvenida ya se mostró
if (!localStorage.getItem('bienvenidaMostrada')) {
    // Mostrar el mensaje de bienvenida
    document.getElementById('bienvenida').classList.remove('ocultar');

    // Marcar que el mensaje de bienvenida se ha mostrado
    localStorage.setItem('bienvenidaMostrada', true);
}

// Ocultar el mensaje de bienvenida cuando se hace clic en un enlace
document.querySelectorAll('.naver a').forEach(link => {
    link.addEventListener('click', () => {
        document.getElementById('bienvenida').style.display = 'none';
    });
});




//******************************************************************

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

function actualizarContador() {
    const cartCounter = document.getElementById('cart-counter');
    const totalItems = Object.values(carrito).reduce((acc, cur) => acc + cur, 0);
    cartCounter.textContent = totalItems;
    cartCounter.style.display = 'block';
}

document.querySelector('.logo-container').addEventListener('click', function() {
    const cartCounter = parseInt(document.getElementById('cart-counter').textContent);
    if (cartCounter !== 0) {
        localStorage.setItem('carrito', JSON.stringify(carrito));
        window.location.href = 'carrito.html'; // Cambiar por la ruta correcta de la página del carrito de la compra
    }
});

