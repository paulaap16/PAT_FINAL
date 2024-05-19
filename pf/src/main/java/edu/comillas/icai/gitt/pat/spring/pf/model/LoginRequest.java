package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {}
