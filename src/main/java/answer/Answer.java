package answer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private UUID id;
    private String answer;
    private boolean correct;
    private UUID testId;
}
