package project.grandmasfood.utils;

public class ErrorMessages {
    public static final String NO_ID_PRODUCT_FOUND_EXCEPTION = "No product found with the given UUID";
    public static final String DUPLICATE_FANTASY_NAME_EXCEPTION = "The product with this fantasy name already exists";
    public static final String NO_CLIENT_FOUND_EXCEPTION = "No client found";
    public static final String NO_KEYWORD = "The keyword is empty";
    public static final String INVALID_PRODUCT_DATA_NAME_EXCEPTION = "Invalid product name too long";
    public static final String INVALID_PRODUCT_DATA_DESCRIPTION_EXCEPTION = "Invalid product description too long";
    public static final String NO_DATA_FOUND_EXCEPTION = "No products found";
    public static final String INVALID_PRODUCT_DATA_PRICE_EXCEPTION = "Invalid product price";

    //Client-Error-Messages
    public static final String CLIENT_NOT_FOUND = "Client not found";
    public static final String CLIENT_ALREADY_EXISTS = "Client already exists";
    public static final String INVALID_ADDRESS = "Invalid delivery address";
    public static final String INVALID_DOCUMENT = "Invalid document";
    public static final String INVALID_PHONE = "Invalid phone number";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String DUPLICATE_DOCUMENT = "Document already exists";

    //Order-Error-Messages
    public static final String ORDER_NOT_FOUND = "Order not found";

    private ErrorMessages() {
        throw new IllegalStateException("Utility class");
    }
}
