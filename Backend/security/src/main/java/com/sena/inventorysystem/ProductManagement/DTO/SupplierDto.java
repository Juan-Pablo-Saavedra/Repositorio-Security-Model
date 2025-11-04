package com.sena.inventorysystem.ProductManagement.DTO;

public class SupplierDto {

    private Long id;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private String address;

    // Constructors
    public SupplierDto() {}

    public SupplierDto(Long id, String name, String contactEmail, String contactPhone, String address) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}