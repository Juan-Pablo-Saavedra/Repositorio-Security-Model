package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.Service.IClientService;
import com.sena.inventorysystem.Infrastructure.DTO.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDto clientDto) {
        try {
            Client client = new Client();
            client.setName(clientDto.getName());
            client.setEmail(clientDto.getEmail());
            client.setPhone(clientDto.getPhone());
            client.setAddress(clientDto.getAddress());

            ClientDto createdClient = clientService.create(client);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Cliente creado exitosamente", createdClient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al crear el cliente: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        try {
            Client client = new Client();
            client.setName(clientDto.getName());
            client.setEmail(clientDto.getEmail());
            client.setPhone(clientDto.getPhone());
            client.setAddress(clientDto.getAddress());

            ClientDto updatedClient = clientService.update(id, client);
            return ResponseEntity.ok(new ApiResponse(true, "Cliente actualizado exitosamente", updatedClient));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al actualizar el cliente: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "Cliente eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Error al eliminar el cliente: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ClientDto client = clientService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cliente encontrado", client));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Cliente no encontrado: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ClientDto> clients = clientService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Clientes obtenidos exitosamente", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener clientes: " + e.getMessage(), null));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            ClientDto client = clientService.findByEmail(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cliente encontrado", client));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Cliente no encontrado: " + e.getMessage(), null));
        }
    }
}