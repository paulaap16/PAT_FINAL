
function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}

function datosPago(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/paulaphotography/pedido/cesta/fin', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}})
    .then(response => {
      if (response.ok) mostrarAviso("se han registrado los datos correctamente");
      else mostrarAviso('✖︎ Error en el registro. Compruebe que los datos están bien introducidos', 'error');
    });
}