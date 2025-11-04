package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Repository.ClientRepository;
import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Service.interfaces.IClientService;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDto create(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new BusinessException("Cliente con email " + client.getEmail() + " ya existe");
        }
        client.setCreatedBy("system");
        Client savedClient = clientRepository.save(client);
        return convertToDto(savedClient);
    }

    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ClientDto getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));
        return convertToDto(client);
    }

    public ClientDto update(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));

        if (!existingClient.getEmail().equals(client.getEmail()) && clientRepository.existsByEmail(client.getEmail())) {
            throw new BusinessException("Cliente con email " + client.getEmail() + " ya existe");
        }

        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());
        existingClient.setUpdatedBy("system");

        Client updatedClient = clientRepository.save(existingClient);
        return convertToDto(updatedClient);
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new BusinessException("Cliente no encontrado");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDto findById(Long id) {
        return getById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return getAll();
    }

    @Override
    public ClientDto findByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Cliente con email " + email + " no encontrado"));
        return convertToDto(client);
    }

    @Override
    public List<ClientDto> findByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ClientDto convertToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getCreatedAt(),
                client.getUpdatedAt(),
                client.getCreatedBy(),
                client.getUpdatedBy()
        );
    }
}