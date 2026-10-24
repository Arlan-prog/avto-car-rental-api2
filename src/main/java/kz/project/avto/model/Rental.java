package kz.project.avto.model;

public class Rental {
    private final Long id;
    private final Long customerId;
    private final Long carId;
    private final int days;
    private final String status;

    private Rental(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.carId = builder.carId;
        this.days = builder.days;
        this.status = builder.status;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getCarId() { return carId; }
    public int getDays() { return days; }
    public String getStatus() { return status; }

    public static class Builder {
        private Long id;
        private Long customerId;
        private Long carId;
        private int days;
        private String status = "ACTIVE";

        public Builder customerId(Long customerId) { this.customerId = customerId; return this; }
        public Builder carId(Long carId) { this.carId = carId; return this; }
        public Builder days(int days) { this.days = days; return this; }
        public Builder id(Long id) { this.id = id; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public Rental build() {
            if (id != null && id <= 0) {
                throw new IllegalArgumentException("id must be positive");
            }
            if (customerId == null || customerId <= 0) {
                throw new IllegalArgumentException("customerId must be positive");
            }
            if (carId == null || carId <= 0) {
                throw new IllegalArgumentException("carId must be positive");
            }
            if (days <= 0) {
                throw new IllegalArgumentException("days must be positive");
            }
            if (!"ACTIVE".equals(status) && !"FINISHED".equals(status)) {
                throw new IllegalArgumentException("status must be ACTIVE or FINISHED");
            }
            return new Rental(this);
        }
    }
}
