package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticuloResponse (
    @NotBlank@NotNull String url,
    @NotBlank@NotNull Size size,
    @NotBlank@NotNull Long cantidad

){}
