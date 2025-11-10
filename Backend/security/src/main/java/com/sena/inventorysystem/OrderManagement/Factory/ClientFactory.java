package com.sena.inventorysystem.OrderManagement.Factory;

import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;

public class ClientFactory {

    public static Client createClient(String name, String email, String phone, String address) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);
        return client;
    }

    public static Client createClientFromDto(ClientDto dto) {
        return createClient(dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAddress());
    }

    public static ClientDto createDtoFromClient(Client client) {
        return new ClientDto(
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
        );
    }
}