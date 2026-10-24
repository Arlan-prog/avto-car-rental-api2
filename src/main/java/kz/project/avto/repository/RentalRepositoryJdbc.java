package kz.project.avto.repository;

import kz.project.avto.model.Rental;
import kz.project.avto.repository.interfaces.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class RentalRepositoryJdbc implements CrudRepository<Rental> {

    private final JdbcTemplate jdbcTemplate;

    public RentalRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Rental> rentalRowMapper = new RowMapper<>() {
        @Override
        public Rental mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Rental.Builder()
                    .id(rs.getLong("id"))
                    .customerId(rs.getLong("customer_id"))
                    .carId(rs.getLong("car_id"))
                    .days(rs.getInt("days"))
                    .build();
        }
    };

    @Override
    public Rental save(Rental rental) {
        String sql = """
                INSERT INTO rentals (customer_id, car_id, days)
                VALUES (?, ?, ?)
                RETURNING id
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                rental.getCustomerId(),
                rental.getCarId(),
                rental.getDays()
        );

        return new Rental.Builder()
                .id(id)
                .customerId(rental.getCustomerId())
                .carId(rental.getCarId())
                .days(rental.getDays())
                .build();
    }

    @Override
    public Optional<Rental> findById(Long id) {
        String sql = "SELECT * FROM rentals WHERE id = ?";
        List<Rental> list = jdbcTemplate.query(sql, rentalRowMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public List<Rental> findAll() {
        String sql = "SELECT * FROM rentals ORDER BY id";
        return jdbcTemplate.query(sql, rentalRowMapper);
    }

    @Override
    public void update(Long id, Rental rental) {
        String sql = """
                UPDATE rentals
                SET customer_id = ?, car_id = ?, days = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, rental.getCustomerId(), rental.getCarId(), rental.getDays(), id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM rentals WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
