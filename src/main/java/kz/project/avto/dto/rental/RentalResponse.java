package kz.project.avto.dto.rental;

public class RentalResponse {

    private Long id;
    private Long customerId;
    private Long carId;
    private int days;

    public RentalResponse(Long id, Long customerId, Long carId, int days) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCarId() {
        return carId;
    }

    public int getDays() {
        return days;
    }
}
