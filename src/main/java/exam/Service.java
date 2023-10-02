package exam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service {

    void save(Exam exam);

    void update(Exam exam);

    void delete(UUID id);

    boolean isExists(UUID id);

    Optional<Exam> findById(UUID id);

    List<Exam> findAll();


}
