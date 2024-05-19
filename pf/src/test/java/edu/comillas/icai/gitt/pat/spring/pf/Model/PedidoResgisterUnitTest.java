/*package edu.comillas.icai.gitt.pat.spring.pf.Model;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Articulo;
import edu.comillas.icai.gitt.pat.spring.pf.model.ArticuloRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import edu.comillas.icai.gitt.pat.spring.pf.model.Size;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PedidoResgisterUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    //si los campos introducidos son correctos no debería haber ninguna violation
    @Test
    public void testValidRequest() {
        // Given ...
        ArticuloRequest articulo = new ArticuloRequest(
                1L, Size.LARGE, ""
        );
        // When ...
        Set<ConstraintViolation<ArticuloRequest>> violations =
                validator.validate(articulo);
        // Then ...
        assertEquals(violations.size(), 1);
    }

}*/
