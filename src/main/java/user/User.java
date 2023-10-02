package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private UUID id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private LocalDateTime created;
}
