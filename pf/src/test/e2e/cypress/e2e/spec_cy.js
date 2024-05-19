describe('Registro y login', () => {
  const email = Date.now() + '@email.com'
  const pass = 'aaaaaaA1';
  const name = 'Mr.Nombre'
  const direccion = 'calle Usuario 1';
  const telefono = '612345678';
  const tarjeta = "ES73 0000 0000 0000 0000";
  const fecha = '19 Mayo 2027';
  const cvv = '789'

  it('Registro correcto', () => {
    cy.visit('http://localhost:8080/registro.html')
    cy.get('[name="name"]').type(name)
    cy.get('[name="name"]').should('have.value', name)
    cy.get('[name="email"]').type(email)
    cy.get('[name="email"]').should('have.value', email)
    cy.get('[name="password"]').type(pass)
    cy.get('[name="password"]').should('have.value', pass)
    cy.get('[name="password2"]').type(pass)
    cy.get('[name="password2"]').should('have.value', pass)
    cy.contains('Registrarse').click()
    cy.url().should('include', '/login.html')
    cy.contains('¡Registrado! Prueba a entrar')
  })

  it('Login correcto', () => {
    cy.visit('http://localhost:8080/login.html')
    cy.get('[name="email"]').type(email)
    cy.get('[name="email"]').should('have.value', email)
    cy.get('[name="password"]').type(pass)
    cy.get('[name="password"]').should('have.value', pass)
    cy.contains('Entrar').click()
    cy.url().should('include', '/app.html')
    cy.contains('Bienvenido')
    cy.contains('Inicio')
    cy.contains('Darse de baja')

  })

  it('addArticulo correcto', () => {
      cy.visit('http://localhost:8080/pagar.html')
      cy.get('[name="nombre"]').type(text)
      cy.get('[name="nombre"]').should('have.value', name)
      cy.get('[name="direccion"]').type(text)
      cy.get('[name="direccion"]').should('have.value', direccion)
      cy.get('[name="telefono"]').type(tel)
      cy.get('[name="telefono"]').should('have.value', telefono)
      cy.get('[name="email"]').type(email)
      cy.get('[name="email"]').should('have.value', email)
      cy.get('[name="tarjeta"]').type(text)
      cy.get('[name="tarjeta"]').should('have.value', tarjeta)
      cy.get('[name="fecha_vencimiento"]').type(text)
      cy.get('[name="fecha_vencimiento"]').should('have.value', fecha)
      cy.get('[name="cvv"]').type(text)
      cy.get('[name="cvv"]').should('have.value', cvv)

      cy.contains('Finalizar y Pagar').click()
      cy.url().should('include', '/fin.html')
      cy.contains('Compra finalizada')
    })
})
