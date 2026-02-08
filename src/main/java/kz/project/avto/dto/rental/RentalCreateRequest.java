package kz.project.avto.dto.rental;

public class RentalCreateRequest {

    private Long customerId;
    private Long carId;
    private int days;

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
