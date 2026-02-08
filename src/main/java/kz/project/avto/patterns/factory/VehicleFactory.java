package kz.project.avto.patterns.factory;

import kz.project.avto.model.EconomyCar;
import kz.project.avto.model.LuxuryCar;
import kz.project.avto.model.VehicleBase;

public class VehicleFactory {

    public static VehicleBase createVehicle(
            VehicleType type,
            Long id,
            String brand,
            String model,
            double pricePerDay
    ) {
        return switch (type) {
            case ECONOMY -> new EconomyCar(id, brand, model, pricePerDay);
            case LUXURY -> new LuxuryCar(id, brand, model, pricePerDay);
        };
    }
}
