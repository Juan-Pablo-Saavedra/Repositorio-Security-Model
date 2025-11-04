package com.sena.inventorysystem.OrderManagement.Controller;

import com.sena.inventorysystem.OrderManagement.Entity.Client;
import com.sena.inventorysystem.OrderManagement.DTO.ClientDto;
import com.sena.inventorysystem.OrderManagement.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody Client client) {
        ClientDto createdClient = clientService.create(client);
        return ResponseEntity.ok(createdClient);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<ClientDto> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        ClientDto client = clientService.getById(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody Client client) {
        ClientDto updatedClient = clientService.update(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDto> findByEmail(@PathVariable String email) {
        ClientDto client = clientService.findByEmail(email);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientDto>> findByName(@RequestParam String name) {
        List<ClientDto> clients = clientService.findByName(name);
        return ResponseEntity.ok(clients);
    }
}