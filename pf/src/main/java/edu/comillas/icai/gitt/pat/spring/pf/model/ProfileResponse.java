package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileResponse(
        @NotBlank @NotNull
        String name,
        @NotBlank @NotNull
        String email,
        @NotBlank @NotNull
        Role role
) {}
