package kz.project.avto.model;

public class Rental {

    private Long id;
    private Long customerId;
    private Long carId;
    private int days;

    private Rental(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.carId = builder.carId;
        this.days = builder.days;
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

    // ===== BUILDER =====
    public static class Builder {
        private Long id;
        private Long customerId;
        private Long carId;
        private int days;

        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public Builder days(int days) {
            this.days = days;
            return this;
        }

        // optional
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Rental build() {
            if (customerId == null) {
                throw new IllegalArgumentException("customerId is required");
            }
            if (carId == null) {
                throw new IllegalArgumentException("carId is required");
            }
            if (days <= 0) {
                throw new IllegalArgumentException("days must be > 0");
            }
            return new Rental(this);
        }
    }
}
