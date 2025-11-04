package com.sena.inventorysystem.OrderManagement.Factory;

import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Entity.Order;

import java.math.BigDecimal;

public class OrderFactory {

    public static Order createOrder(Client client, BigDecimal total) {
        Order order = new Order();
        order.setClient(client);
        order.setTotal(total);
        return order;
    }

    public static Order createOrderWithAudit(Client client, BigDecimal total, String createdBy) {
        Order order = createOrder(client, total);
        order.setCreatedBy(createdBy);
        return order;
    }
}