package com.sena.inventorysystem.OrderManagement.Factory;

import com.sena.inventorysystem.OrderManagement.Entity.Order;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.DTO.OrderDto;

import java.math.BigDecimal;

public class OrderFactory {

    public static Order createOrder(Client client, BigDecimal total) {
        Order order = new Order();
        order.setClient(client);
        order.setTotal(total);
        return order;
    }

    public static Order createOrderFromDto(OrderDto dto, Client client) {
        return createOrder(client, dto.getTotal());
    }

    public static OrderDto createDtoFromOrder(Order order) {
        return new OrderDto(
                order.getId(),
                order.getClient().getId(),
                order.getClient().getName(),
                order.getOrderDate(),
                order.getTotal(),
                order.getStatus().toString()
        );
    }
}