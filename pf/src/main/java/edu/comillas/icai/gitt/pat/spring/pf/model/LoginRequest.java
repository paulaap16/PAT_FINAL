package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank@Email@NotNull
        String email,
        @NotBlank@NotNull
        String password
) {}
