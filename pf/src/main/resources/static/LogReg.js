function compruebaPass() {
console.log('entro en compruebo pass');
  let correcto = false;
  correcto = document.getElementById("password").value ===
             document.getElementById("passwordValidate").value;
  if (correcto) mostrarAviso();
  else mostrarAviso('✖︎ Contraseña inválida', 'error');
  return correcto;
}

function registrarUsuario(datosJsonFormulario) {
  //if (!compruebaPass()) return;
  console.log('entro en registrarUsuario');
  fetch('/paulaphotography/user', {
    method: 'post',
    body: datosJsonFormulario,
    headers: {'content-type': 'application/json'},
    credentials: 'include'
  })
  .then(response => {
    console.log('Received response:', response);
    if (response.ok){
      console.log('response.ok');
      location.href = 'blog.html';
      mostrarAvisoReg('todo okay', 'error');
    }
    else{
        console.log('response not ok');
        mostrarAvisoReg('✖︎ Error en el registro. Si ya tiene una cuenta, haga un Login', 'error');
        //location.href = 'blog.html';
    }
  })
    .catch(error => {
      console.log('error');
      console.error('Error:', error);
      mostrarAvisoReg('✖︎ Error en el registro. Si ya tiene una cuenta, haga un Login', 'error');
      //location.href = 'blog.html';  // Redirect in case of fetch error
    });
}

function logUsuario(datosJsonFormulario) {
  //if (!compruebaPass()) return;
  fetch('/paulaphotography/user/session', {
    method: 'post',
    body: datosJsonFormulario,
    headers: {'content-type': 'application/json'},
    credentials: 'include'
  })
  .then(response => {
    if (response.created) location.href = 'blog.html';
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
console.log('entro en form2json');
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}