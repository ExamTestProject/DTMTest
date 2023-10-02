package answer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerService implements Service {
    private static final AnswerService answerService = new AnswerService();
    private static final AnswerRepository answerRepository = AnswerRepository.getInstance();


    @Override
    public void save(Answer entity) {
        answerRepository.save(entity);

    }

    @Override
    public void update(Answer entity) {
        answerRepository.update(entity);

    }

    @Override
    public void delete(UUID id) {
        answerService.delete(id);

    }

    @Override
    public boolean isExists(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Answer> findById(UUID id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public static AnswerService getInstance(){
        return answerService;
    }

}
