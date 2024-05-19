package edu.comillas.icai.gitt.pat.spring.pf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProfileRequest(
        @NotBlank@NotNull String name,
        @NotBlank@NotNull Role role,
        // Patrón: al menos una mayúscula, una minúscula, y un número, y de longitud más de 7
        @NotBlank@NotNull @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$")
        String password
) {
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
