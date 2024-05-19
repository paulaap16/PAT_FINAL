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
  fetch('/paulaphotography/user', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}, credentials: 'include'}})
    .then(response => {
      if (response.ok) location.href = 'blog.html';
      else if (response.status === 409) mostrarAvisoReg('✖︎ Usuario ya registrado', 'error');
      else mostrarAviso('✖︎ Error en el registro. Si ya tiene una cuenta, haga un Login', 'error');
    });
}

function logUsuario(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/paulaphotography/user/session', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}, credentials: 'include'}})
    .then(response => {
      if (response.ok) location.href = 'blog.html';
      else if (response.status === 409) mostrarAvisoLog('✖︎ Usuario ya registrado', 'error');
      else mostrarAviso('✖︎ Error en el login. Si no tiene cuenta, registrese primero', 'error');
    });
}

function mostrarAvisoLog(texto, tipo) {
  const aviso = document.getElementById("aviso-login");
  aviso.textContent = texto;
  aviso.className = tipo;
}
function mostrarAvisoReg(texto, tipo) {
  const aviso = document.getElementById("aviso-registro");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

