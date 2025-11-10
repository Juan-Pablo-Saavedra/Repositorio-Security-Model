package com.sena.inventorysystem.OrderManagement.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    private String name;

    @Column(length = 100, unique = true)
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Column(length = 20)
    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    private String phone;

    @Column(columnDefinition = "TEXT")
    @Size(max = 500, message = "La dirección no debe exceder 500 caracteres")
    private String address;

    // Constructors
    public Client() {}

    public Client(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}