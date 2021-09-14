package be.brussel.schoolgrappling.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    UNDEFINED("Undefined"),
    PRIMARY_1("1e primary"),
    PRIMARY_2("2e primary"),
    PRIMARY_3("3e primary"),
    PRIMARY_4("4e primary"),
    PRIMARY_5("5e primary"),
    PRIMARY_6("6e primary"),
    SECONDARY_1("1e secondary"),
    SECONDARY_2("2e secondary"),
    SECONDARY_3("3e secondary"),
    SECONDARY_4("4e secondary"),
    SECONDARY_5("5e secondary"),
    SECONDARY_6("6e secondary");

    private String name;
}
