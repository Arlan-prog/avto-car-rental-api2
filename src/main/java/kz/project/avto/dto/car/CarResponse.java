package kz.project.avto.dto.car;

public class CarResponse {

    private Long id;
    private String type;
    private String brand;
    private String model;
    private double pricePerDay;

    public CarResponse(Long id, String type, String brand, String model, double pricePerDay) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
}
