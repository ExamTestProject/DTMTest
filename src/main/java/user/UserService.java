package user;

import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.nio.charset.StandardCharsets;
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

    public boolean checkByUsernameAndPassword(String username, String password) {
        String password1 = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        for (User user : findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password1)) {
                return true;
            }
        }
        return false;
    }
}
