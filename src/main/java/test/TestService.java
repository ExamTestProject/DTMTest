package test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestService implements Service {
    private static final TestRepository testRepository = TestRepository.getInstance();

    @Override
    public void save(Test test) {
        testRepository.save(test);
    }

    @Override
    public void update(Test test) {
        testRepository.update(test);
    }

    @Override
    public void delete(UUID id) {
        testRepository.delete(id);
    }

    @Override
    public Optional<Test> findById(UUID id) {
        return testRepository.findById(id);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public boolean isExists(UUID id) {
        return findById(id).isPresent();
    }
}
