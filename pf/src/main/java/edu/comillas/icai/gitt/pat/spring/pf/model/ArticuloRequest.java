package edu.comillas.icai.gitt.pat.spring.pf.model;

import edu.comillas.icai.gitt.pat.spring.pf.Entity.Token;

public record ArticuloRequest(
        Token token,
        Size size,
        Long cantidad,
        String url
) {

}
