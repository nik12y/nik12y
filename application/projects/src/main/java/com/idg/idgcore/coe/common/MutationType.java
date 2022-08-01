package com.idg.idgcore.coe.common;

public enum MutationType {
    ADDITION("A"), UPDATE("U"), DELETION("D"), REOPEN("R"), INQUIRE("I"), AUTHORIZE("Z"), CLOSE("C");
    private final String value;

    MutationType (String value) {
        this.value = value;
    }

    public static MutationType fromValue (String str) {
        for (MutationType element : MutationType.values()) {
            if (element.toString().equals(str)) {
                return element;
            }
        }
        return null;
    }

    public static MutationType fromString (String str) {
        for (MutationType element : MutationType.values()) {
            if (element.toString().equals(str)) {
                return element;
            }
        }
        return null;
    }
}
