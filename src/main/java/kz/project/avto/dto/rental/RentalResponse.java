package kz.project.avto.dto.rental;

public class RentalResponse {
    private final Long id;
    private final Long customerId;
    private final Long carId;
    private final int days;
    private final String status;

    public RentalResponse(Long id, Long customerId, Long carId, int days, String status) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.days = days;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getCarId() { return carId; }
    public int getDays() { return days; }
    public String getStatus() { return status; }
}
