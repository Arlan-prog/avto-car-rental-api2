package kz.project.avto.dto.car;

public class CarCreateRequest {

    private String type;   // ECONOMY or LUXURY
    private String brand;
    private String model;
    private double pricePerDay;

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
