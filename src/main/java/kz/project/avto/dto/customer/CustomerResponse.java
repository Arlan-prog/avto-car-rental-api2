package kz.project.avto.dto.customer;

public class CustomerResponse {
    private Long id;
    private String fullName;
    private String phone;

    public CustomerResponse(Long id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
}
