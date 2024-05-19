
document.addEventListener('DOMContentLoaded', function() {
    const blogLink = document.getElementById('blog-link');

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    const userCookie = getCookie('user_session'); // Reemplaza 'user_session' con el nombre de tu cookie

    if (userCookie) {
        blogLink.href = 'blog.html';
    } else {
        blogLink.href = 'LogReg.html';
    }
});


function logout() {
    // Elimina la cookie de sesión
    document.cookie = "user_session=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

    // Redirige a la página de inicio o login
    window.location.href = 'index.html'; // Puedes cambiar 'index.html' por la página de tu elección
}

function checkSession() {
    const sessionCookie = getCookie('user_session');
    if (sessionCookie) {
        document.getElementById('logout-button').style.display = 'block';
    }
}
window.onload = checkSession();