package exam;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExamService implements Service {

    private static final ExamService EXAM_SERVICE = new ExamService();

    private final ExamRepository examRepository = ExamRepository.getInstance();


    @Override
    public void save(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void update(Exam exam) {
        examRepository.update(exam);
    }

    @Override
    public void delete(UUID id) {
        examRepository.delete(id);
    }

    @Override
    public boolean isExists(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Exam> findById(UUID id) {
        return examRepository.findById(id);
    }

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public List<Exam> getExamByLimit(int offset, int limit) {
        return examRepository.getExamByLimit(offset, limit);
    }


    public static ExamService getInstance() {
        return EXAM_SERVICE;
    }

}
