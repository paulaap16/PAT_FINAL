package edu.comillas.icai.gitt.pat.spring.pf.model;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticuloRequest(
        @NotNull Long cantidad,
        @NotNull Size size,
<<<<<<< HEAD
        @NotBlank @NotNull String url
=======
        @NotBlank@NotNull String url
>>>>>>> Inés

) {

}
