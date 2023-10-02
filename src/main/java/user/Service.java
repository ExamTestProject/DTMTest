package user;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service {
    void save(User entity);

    void update(User entity);

    void delete(UUID id);

    Optional<User> findById(UUID id);

    List<User> findAll();

    boolean isExists(UUID id);
}
