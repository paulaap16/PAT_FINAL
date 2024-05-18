package edu.comillas.icai.gitt.pat.spring.pf.UsuarioTest;

import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitario {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidRequirement() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "1234");  //contraseña no cumple requerimientos
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertFalse(violations.isEmpty());//pasa si hay violacion
        assertEquals(1, violations.size());
    }
}
