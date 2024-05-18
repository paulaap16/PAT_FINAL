/*let carrito = {}; // Objeto para almacenar las imágenes seleccionadas y su cantidad

function añadirAlCarrito() {
    const imagen = event.target.parentElement.parentElement.querySelector('img').src;
    
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
        window.location.href = 'carrito.html'; // Cambiar por la ruta correcta de la página del carrito de la compra
    }
});

*/

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
