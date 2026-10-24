package kz.project.avto.repository;

import kz.project.avto.model.Customer;
import kz.project.avto.repository.interfaces.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryJdbc implements CrudRepository<Customer> {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = new RowMapper<>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("full_name"),
                    rs.getString("phone")
            );
        }
    };

    @Override
    public Customer save(Customer customer) {
        String sql = """
                INSERT INTO customers (full_name, phone)
                VALUES (?, ?)
                RETURNING id
                """;
        Long id = jdbcTemplate.queryForObject(sql, Long.class, customer.getFullName(), customer.getPhone());
        return new Customer(id, customer.getFullName(), customer.getPhone());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        List<Customer> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers ORDER BY id", rowMapper);
    }

    @Override
    public void update(Long id, Customer customer) {
        String sql = "UPDATE customers SET full_name = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getPhone(), id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM customers WHERE id = ?", id);
    }
}
