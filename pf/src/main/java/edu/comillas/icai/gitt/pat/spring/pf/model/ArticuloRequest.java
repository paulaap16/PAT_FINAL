package edu.comillas.icai.gitt.pat.spring.pf.model;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticuloRequest(
        @NotBlank@NotNull Long cantidad,
        @NotBlank@NotNull Size size,
        @NotBlank@NotNull String url

) {

}
