package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompradorRequest(
        @NotBlank @NotNull String name,
        @NotBlank @NotNull String email,
        @NotBlank @NotNull String direccion,
        @NotBlank @NotNull String telefono,
        @NotBlank @NotNull String tarjeta,
        @NotBlank @NotNull  String fechaVencimientoTarjeta,
        @NotBlank @NotNull  String cvv

){}
