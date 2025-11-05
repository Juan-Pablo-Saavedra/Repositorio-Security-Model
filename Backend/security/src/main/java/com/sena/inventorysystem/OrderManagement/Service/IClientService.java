package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Entity.Client;

import java.util.List;

public interface IClientService {

    ClientDto create(Client client);

    ClientDto update(Long id, Client client);

    void delete(Long id);

    ClientDto findById(Long id);

    List<ClientDto> findAll();

    ClientDto findByEmail(String email);
}