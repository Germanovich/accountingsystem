package com.hermanovich.accountingsystem.util;

public enum MessageForUser {

    MESSAGE_NOT_FOUND_ALL_USER_DATA("Not found all user data"),
    MESSAGE_NOT_FOUND_ALL_PROFILE_DATA("Not found all profile data"),
    MESSAGE_NOT_FOUND_ALL_CAR_DATA("Not found all car data"),
    MESSAGE_NOT_FOUND_ALL_ORDER_DATA("Not found all order data"),
    MESSAGE_NOT_FOUND_ALL_REQUEST_DATA("Not found all request data"),
    MESSAGE_NOT_FOUND_ALL_SETTING_DATA("Not found all setting data"),
    MESSAGE_NOT_FOUND_ALL_CONTACT_PERSON_DATA("Not all contact person data"),
    MESSAGE_THIS_LOGIN_ALREADY_EXISTS("This login already exists"),
    MESSAGE_NO_SUCH_ORDER_EXISTS("No such order exists"),
    MESSAGE_NO_SUCH_REQUEST_EXISTS("No such request exists"),
    MESSAGE_NO_SUCH_CAR_EXISTS("No such car exists"),
    MESSAGE_NO_SUCH_USER_EXISTS("No such user exists"),
    MESSAGE_NO_SUCH_FIRM_EXISTS("No such firm exists"),
    MESSAGE_NO_SUCH_CONTACT_PERSON_EXISTS("No such contact person exists"),
    MESSAGE_NO_SUCH_CATALOG_EXISTS("No such catalog exists"),
    MESSAGE_NO_SUCH_SETTING_EXISTS("No such setting exists"),
    MESSAGE_CATALOG_NAME_IS_EMPTY("Catalog name is empty"),
    MESSAGE_THE_NUMBER_OF_DAYS_IS_MORE_OR_LESS_THAN_ALLOWED("The number of days is more than allowed"),
    MESSAGE_USER_ALREADY_HAS_AN_ACTIVE_ORDER("User already has an active order"),
    MESSAGE_USER_ALREADY_HAS_A_REQUEST("User already has a request"),
    MESSAGE_FIRM_HAS_CARS_OR_CONTACT_PERSONS("Firm has cars or contact persons"),
    MESSAGE_CAR_HAS_ORDERS_OR_REQUESTS("Car has orders or requests"),
    MESSAGE_CATALOG_HAS_CHILD_CATALOGS_OR_CARS("Catalog has child catalogs or cars"),
    MESSAGE_USER_HAS_ORDERS_OR_REQUESTS("User has orders or requests"),
    MESSAGE_CAR_NOT_AVAILABLE("Сar is not available"),
    MESSAGE_CAR_AVAILABLE("Сar is available"),
    MESSAGE_SAVE_ERROR("Save error"),
    MESSAGE_NO_RECORDS_FOUND("No records found"),
    MESSAGE_FAILED_TO_GET_STATUS("Failed to get status"),
    MESSAGE_TOKEN_IS_INVALID("token is invalid"),
    MESSAGE_FAILED_TO_GET_ROLE("Failed to get role"),
    MESSAGE_USER_IS_NOT_FOUND("User is not found"),
    MESSAGE_USER_IS_NOT_AUTHORIZED("User is not authorized"),
    MESSAGE_REQUEST_IS_NOT_FOUND("Request is not found"),
    MESSAGE_PASSWORD_IS_MISSING("Password is missing"),
    MESSAGE_FIRM_NAME_IS_MISSING("Firm name is missing"),
    MESSAGE_NO_REQUESTS("No requests"),
    MESSAGE_ORDER_CREATION_IS_PROHIBITED("Order creation is prohibited"),
    MESSAGE_REQUEST_CREATION_IS_PROHIBITED("Request creation is prohibited"),
    MESSAGE_USER_CREATION_IS_PROHIBITED("User creation is prohibited"),
    MESSAGE_AUTHENTICATION_EXCEPTION("Authentication exception"),
    MESSAGE_INVALID_TOKEN("Invalid token"),
    MESSAGE_REQUEST_METHOD_NOT_SUPPORTED("Request method not supported"),
    MESSAGE_MEDIA_TYPE_NOT_SUPPORTED("Media type not supported"),
    MESSAGE_MEDIA_TYPE_NOT_ACCEPTABLE("Media type not acceptable"),
    MESSAGE_SERVER_ERROR("Server error"),
    MESSAGE_BAD_REQUEST("Bad request"),
    MESSAGE_NO_HANDLER_FOUND("No handler found"),
    MESSAGE_SERVICE_UNAVAILABLE("Service unavailable"),
    MESSAGE_NOT_ENOUGH_RIGHTS("Not enough rights"),
    MESSAGE_CANNOT_DELETE("Cannot delete");

    private final String message;

    MessageForUser(String message) {
        this.message = message;
    }

    public String get() {
        return (this.message);
    }
}
