package kz.project.avto.model;

public class Customer {
    private Long id;
    private String fullName;
    private String phone;

    public Customer(Long id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
}
