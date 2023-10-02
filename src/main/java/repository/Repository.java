package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, ENTITY> {
    void save(ENTITY entity);

    void update(ENTITY entity);

    void delete(ID id);

    Optional<ENTITY> findById(ID id);

    List<ENTITY> findAll();
}
