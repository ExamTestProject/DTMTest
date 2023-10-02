package user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService implements Service {
    private final UserRepository userRepository = UserRepository.getInstance();

    private final static UserService userService = new UserService();

    @Override
    public void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void update(User entity) {
        userRepository.update(entity);
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isExists(UUID id) {
        return findById(id).isPresent();
    }

    public static UserService getInstance() {
        return userService;
    }

}
