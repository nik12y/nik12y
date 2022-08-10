package com.idg.idgcore.coe.exception;

public enum Error {
    DATA_ACCESS_ERROR("IDC_COE_0001", "A database access error has occurred."),
    DUPLICATE_RECORD("IDC_COE_0002", "Record already exists."),
    UNAUTHORIZED_RECORD_ALREADY_EXISTS("IDC_COE_0003", "Record already exists.Please, authorize existing record and retry."),
    JSON_PARSING_ERROR("IDC_COE_0004", "Error occurred while parsing the record."),
    NO_RECORD_FOUND("IDC_COE_0005", "No records found."),
    VALIDATION_FAILED("IDC_COE_0006", "Record validation failed. No mapping found for record."),
    KAFKA_MESSAGE_PUBLISH_FAILED("IDC_COE_0007", "Failure while sending message over kafka.");

    private final String code;
    private final String message;

    private Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " : " + message;
    }
}