package be.brussel.schoolgrappling.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Function {

    VAKLEERKRACHT("vakleerkracht"),
    LO_LEERKRACHT("LO leerkracht"),
    DIRECTEUR("directeur");

    private String name;

}
