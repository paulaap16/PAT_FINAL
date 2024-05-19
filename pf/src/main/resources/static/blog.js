

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











/*

// Verificar si el usuario ha iniciado sesión
if (!usuarioHaIniciadoSesion()) {     // Si el usuario no ha iniciado sesión, redirigir a la página de inicio de sesión o registro
    var blogLink = document.getElementById('blog-link');
    blogLink.setAttribute('href', 'LogReg.html'); // o 'register.html' según necesites
} else {
    blogLink.setAttribute('href', 'blog.html'); // o 'register.html' según necesites
}
function usuarioHaIniciadoSesion() {
// Lógica para verificar si el usuario ha iniciado sesión
// Puedes usar cookies,  de sesión u otras técnicas de autenticación aquí
// Devuelve true si el usuario ha iniciado sesión, de lo contrario, devuelve false
}

*/