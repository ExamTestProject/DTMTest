package test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service {
    void save(Test test);

    void update(Test test);

    void delete(UUID id);

    Optional<Test> findById(UUID id);

    List<Test> findAll();

    boolean isExists(UUID id);

    List<Test> findByExamId(UUID examId);
}
