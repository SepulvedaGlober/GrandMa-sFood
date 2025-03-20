package project.grandmasfood.utils;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATE_FANTASY_NAME("E1002"),
    NO_PRODUCT_FOUND("E1003"),
    NO_PRODUCT_UUID_FOUND("E1004"),
    NO_KEYWORD("E1005"),
    NO_DATA_FOUND("E1008"),
    CLIENT_NOT_FOUND("E2001"),
    CLIENT_ALREADY_EXISTS("E2002"),
    INVALID_ADDRESS("E2003"),
    INVALID_DOCUMENT("E2004"),
    INVALID_PHONE("E2005"),
    DUPLICATE_DOCUMENT("E2006"),
    INVALID_EMAIL("E2007"),
    INVALID_DOCUMENT_CHANGE("E2009"),
    ORDER_NOT_FOUND("E3001"),
    INTERNAL_SERVER_ERROR("E5000");

    private final String code;
    ErrorCode(String code) {
        this.code = code;
    }
}
