package kz.project.avto.repository;

import kz.project.avto.model.VehicleBase;
import kz.project.avto.patterns.factory.VehicleFactory;
import kz.project.avto.patterns.factory.VehicleType;
import kz.project.avto.repository.interfaces.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepositoryJdbc implements CrudRepository<VehicleBase> {

    private final JdbcTemplate jdbcTemplate;

    public CarRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<VehicleBase> carRowMapper = new RowMapper<>() {
        @Override
        public VehicleBase mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String type = rs.getString("car_type");
            String brand = rs.getString("brand");
            String model = rs.getString("model");
            double price = rs.getDouble("price_per_day");

            return VehicleFactory.createVehicle(
                    VehicleType.valueOf(type),
                    id,
                    brand,
                    model,
                    price
            );
        }
    };

    @Override
    public VehicleBase save(VehicleBase car) {
        String sql = """
                INSERT INTO cars (car_type, brand, model, price_per_day)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                car.getClass().getSimpleName().toUpperCase().contains("LUXURY")
                        ? "LUXURY"
                        : "ECONOMY",
                car.getBrand(),
                car.getModel(),
                car.getPricePerDay()
        );

        return VehicleFactory.createVehicle(
                car.getClass().getSimpleName().toUpperCase().contains("LUXURY")
                        ? VehicleType.LUXURY
                        : VehicleType.ECONOMY,
                id,
                car.getBrand(),
                car.getModel(),
                car.getPricePerDay()
        );
    }

    @Override
    public Optional<VehicleBase> findById(Long id) {
        String sql = "SELECT * FROM cars WHERE id = ?";

        List<VehicleBase> result = jdbcTemplate.query(sql, carRowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public List<VehicleBase> findAll() {
        String sql = "SELECT * FROM cars";
        return jdbcTemplate.query(sql, carRowMapper);
    }

    @Override
    public void update(Long id, VehicleBase car) {
        String sql = """
                UPDATE cars
                SET brand = ?, model = ?, price_per_day = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                car.getBrand(),
                car.getModel(),
                car.getPricePerDay(),
                id
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
