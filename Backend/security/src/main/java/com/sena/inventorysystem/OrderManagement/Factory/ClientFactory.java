package com.sena.inventorysystem.OrderManagement.Factory;

import com.sena.inventorysystem.OrderManagement.Entity.Client;

public class ClientFactory {

    public static Client createClient(String name, String email, String phone, String address) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);
        return client;
    }

    public static Client createClientWithAudit(String name, String email, String phone, String address, String createdBy) {
        Client client = createClient(name, email, phone, address);
        client.setCreatedBy(createdBy);
        return client;
    }
}