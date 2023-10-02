package test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestService implements Service {
    private static final TestRepository testRepository = TestRepository.getInstance();

    private static final TestService TEST_SERVICE = new TestService();

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

    public static TestService getInstance() {
        return TEST_SERVICE;
    }

}
