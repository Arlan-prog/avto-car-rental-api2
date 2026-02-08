package kz.project.avto.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    T save(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void update(Long id, T entity);

    void deleteById(Long id);
}
