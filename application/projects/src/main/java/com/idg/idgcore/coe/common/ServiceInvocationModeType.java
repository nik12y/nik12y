package com.idg.idgcore.coe.common;

public enum ServiceInvocationModeType {
    Regular("R"), Simulation("S"), Draft("D");
    private final String value;

    ServiceInvocationModeType (String value) {
        this.value = value;
    }

    private ServiceInvocationModeType fromString (String value) {
        for (int i = 0; i < ServiceInvocationModeType.values().length; i++) {
            ServiceInvocationModeType serviceInvocationModeType = ServiceInvocationModeType.values()[i];
            if (serviceInvocationModeType.toString().equals(value)) {
                return serviceInvocationModeType;
            }
        }
        throw new IllegalArgumentException(
                "Cannot parse into an element of ServiceInvocationModeType : '" + value + "'");
    }

    @Override
    public String toString () {
        return value;
    }
}
