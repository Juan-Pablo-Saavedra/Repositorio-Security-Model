package com.sena.inventorysystem.OrderManagement.Service;

import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Repository.ClientRepository;
import com.sena.inventorysystem.Infrastructure.exceptions.BusinessException;
import com.sena.inventorysystem.Infrastructure.exceptions.NotFoundException;
import com.sena.inventorysystem.Infrastructure.exceptions.ValidationException;
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

    @Override
    public ClientDto create(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new BusinessException("Cliente con email " + client.getEmail() + " ya existe");
        }
        Client savedClient = clientRepository.save(client);
        return convertToDto(savedClient);
    }

    @Override
    public ClientDto update(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id: " + id));

        if (!existingClient.getEmail().equals(client.getEmail()) && clientRepository.existsByEmail(client.getEmail())) {
            throw new ValidationException("Cliente con email " + client.getEmail() + " ya existe");
        }

        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());

        Client updatedClient = clientRepository.save(existingClient);
        return convertToDto(updatedClient);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado con id: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con id: " + id));
        return convertToDto(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto findByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con email: " + email));
        return convertToDto(client);
    }

    private ClientDto convertToDto(Client client) {
        return new ClientDto(
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
        );
    }
}