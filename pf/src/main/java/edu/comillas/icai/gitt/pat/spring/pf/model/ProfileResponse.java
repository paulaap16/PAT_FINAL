package edu.comillas.icai.gitt.pat.spring.pf.model;

public record ProfileResponse(
        String name,
        String email,
        Role role
) {}
