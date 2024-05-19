package edu.comillas.icai.gitt.pat.spring.pf.Model;

import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


//esta clase se emplea para comprobar que los campos que se reciben a la hora de hacer el register siguen las restricciones que se piden,
// como que el correo siga el formato seguido y la contraseña tenga unos caracteres determinados
public class RegisterUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    //si los campos introducidos son correctos no debería haber ninguna violation
    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1", "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPassword() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "1234", "1234");  //contraseña no cumple requerimientos
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.size() == 2); // Verificar que se obtiene una sola violación por cada contraseña incorrecta
        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("{jakarta.validation.constraints.Pattern.message}", violation.getMessageTemplate()); // Verificar si el error es el adecuado
    }

    //Si el email no tiene el formato estandar se obtiene una violation
    @Test
    public void testInvalidEmail() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "email.com",
                Role.USER, "aaaaaaA1","aaaaaaA1" );
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.size() == 1); // Verificar que se obtiene una sola violación
        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("{jakarta.validation.constraints.Email.message}", violation.getMessageTemplate()); // Verificar si el error es el adecuado

    }

    // si el nombre del usuario está vacío, debería saltar un violation
    @Test
    public void testInvalidName(){
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "", "nombre@email.com",
                Role.USER, "aaaaaaA1", "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);

        // Then ...
        assertTrue(violations.size() == 1); // Verificar que se obtiene una sola violación
        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("{jakarta.validation.constraints.NotBlank.message}", violation.getMessageTemplate()); // Verificar si el error es de NotBlank


    }

}
