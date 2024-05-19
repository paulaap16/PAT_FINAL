function compruebaPass() {
  let correcto = false;
  correcto = document.getElementById("password").value ===
             document.getElementById("passwordValidate").value;
  if (correcto) mostrarAviso();
  else mostrarAviso('✖︎ Contraseña inválida', 'error');
  return correcto;
}

function registrarUsuario(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/paulaphotography/user', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}})
    .then(response => {
      if (response.ok) location.href = 'blog.html';
      else if (response.status === 409) mostrarAviso('✖︎ Usuario ya registrado', 'error');
      else mostrarAviso('✖︎ Error en el registro. Si ya tiene una cuenta, haga un Login', 'error');
    });
}

function logUsuario(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/paulaphotography/user/session', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}})
    .then(response => {
      if (response.ok) location.href = 'blog.html';
      else if (response.status === 409) mostrarAviso('✖︎ Usuario ya registrado', 'error');
      else mostrarAviso('✖︎ Error en el login. Si no tiene cuenta, registrese primero', 'error');
    });
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}


function togglePassword(inputId, toggleIcon) {
    const passwordInput = document.getElementById(inputId);
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.textContent = '🙈'; // Cambia el icono al de "ocultar"
    } else {
        passwordInput.type = 'password';
        toggleIcon.textContent = '👁️'; // Cambia el icono al de "mostrar"
    }
}