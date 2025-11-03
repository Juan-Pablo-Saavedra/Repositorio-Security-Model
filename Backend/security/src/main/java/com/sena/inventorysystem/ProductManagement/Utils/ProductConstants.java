package com.sena.inventorysystem.ProductManagement.Utils;

public class ProductConstants {

    // Movement types
    public static final String MOVEMENT_IN = "IN";
    public static final String MOVEMENT_OUT = "OUT";
    public static final String MOVEMENT_ADJUSTMENT = "ADJUSTMENT";

    // Default values
    public static final Integer DEFAULT_MIN_QUANTITY = 0;
    public static final Integer DEFAULT_MAX_QUANTITY = 1000;

    // Error messages
    public static final String PRODUCT_NOT_FOUND = "Producto no encontrado";
    public static final String CATEGORY_NOT_FOUND = "Categoría no encontrada";
    public static final String SUPPLIER_NOT_FOUND = "Proveedor no encontrado";
    public static final String STOCK_NOT_FOUND = "Stock no encontrado";
    public static final String MOVEMENT_NOT_FOUND = "Movimiento no encontrado";
    public static final String INSUFFICIENT_STOCK = "No hay suficiente stock disponible";
    public static final String SKU_ALREADY_EXISTS = "SKU ya existe";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "Nombre de categoría ya existe";
    public static final String SUPPLIER_EMAIL_ALREADY_EXISTS = "Email de proveedor ya existe";

    // Success messages
    public static final String PRODUCT_CREATED = "Producto creado exitosamente";
    public static final String PRODUCT_UPDATED = "Producto actualizado exitosamente";
    public static final String PRODUCT_DELETED = "Producto eliminado exitosamente";
    public static final String CATEGORY_CREATED = "Categoría creada exitosamente";
    public static final String CATEGORY_UPDATED = "Categoría actualizada exitosamente";
    public static final String CATEGORY_DELETED = "Categoría eliminada exitosamente";
    public static final String SUPPLIER_CREATED = "Proveedor creado exitosamente";
    public static final String SUPPLIER_UPDATED = "Proveedor actualizado exitosamente";
    public static final String SUPPLIER_DELETED = "Proveedor eliminado exitosamente";
    public static final String STOCK_CREATED = "Stock creado exitosamente";
    public static final String STOCK_UPDATED = "Stock actualizado exitosamente";
    public static final String STOCK_DELETED = "Stock eliminado exitosamente";
    public static final String MOVEMENT_CREATED = "Movimiento creado exitosamente";

    private ProductConstants() {
        // Utility class
    }
}