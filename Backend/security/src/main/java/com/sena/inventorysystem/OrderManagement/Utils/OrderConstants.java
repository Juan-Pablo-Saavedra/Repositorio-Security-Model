package com.sena.inventorysystem.OrderManagement.Utils;

public class OrderConstants {

    // Order Status Constants
    public static final String ORDER_STATUS_PENDING = "PENDING";
    public static final String ORDER_STATUS_CONFIRMED = "CONFIRMED";
    public static final String ORDER_STATUS_SHIPPED = "SHIPPED";
    public static final String ORDER_STATUS_DELIVERED = "DELIVERED";
    public static final String ORDER_STATUS_CANCELLED = "CANCELLED";

    // Default values
    public static final String DEFAULT_CREATED_BY = "system";
    public static final String DEFAULT_UPDATED_BY = "system";

    // Validation messages
    public static final String CLIENT_REQUIRED = "Cliente es requerido para crear una orden";
    public static final String CLIENT_NOT_FOUND = "Cliente no encontrado";
    public static final String ORDER_NOT_FOUND = "Orden no encontrada";
    public static final String EMAIL_ALREADY_EXISTS = "Cliente con email %s ya existe";

    // Database constraints
    public static final int MAX_NAME_LENGTH = 200;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 20;
}