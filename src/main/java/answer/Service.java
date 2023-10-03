package answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service {
    void save(Answer entity);

    void update(Answer entity);

    void delete(UUID id);

    boolean isExists(UUID id);

    Optional<Answer> findById(UUID id);

    List<Answer> findAll();

    List<Answer> findByTestId(UUID id);


}
