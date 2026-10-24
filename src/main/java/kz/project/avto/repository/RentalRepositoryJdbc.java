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
                    .status(rs.getString("status"))
                    .build();
        }
    };

    @Override
    public Rental save(Rental rental) {
        String sql = """
                INSERT INTO rentals (customer_id, car_id, days, status)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;
        Long id = jdbcTemplate.queryForObject(
                sql, Long.class, rental.getCustomerId(), rental.getCarId(),
                rental.getDays(), rental.getStatus()
        );
        return new Rental.Builder()
                .id(id)
                .customerId(rental.getCustomerId())
                .carId(rental.getCarId())
                .days(rental.getDays())
                .status(rental.getStatus())
                .build();
    }

    @Override
    public Optional<Rental> findById(Long id) {
        List<Rental> rentals = jdbcTemplate.query(
                "SELECT * FROM rentals WHERE id = ?", rentalRowMapper, id
        );
        return rentals.stream().findFirst();
    }

    @Override
    public List<Rental> findAll() {
        return jdbcTemplate.query("SELECT * FROM rentals ORDER BY id", rentalRowMapper);
    }

    public boolean closeActiveRental(Long id) {
        return jdbcTemplate.update(
                "UPDATE rentals SET status = 'FINISHED' WHERE id = ? AND status = 'ACTIVE'",
                id
        ) == 1;
    }

    @Override
    public void update(Long id, Rental rental) {
        String sql = """
                UPDATE rentals
                SET customer_id = ?, car_id = ?, days = ?
                WHERE id = ? AND status = 'ACTIVE'
                """;
        jdbcTemplate.update(sql, rental.getCustomerId(), rental.getCarId(), rental.getDays(), id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM rentals WHERE id = ?", id);
    }
}
