package test.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestView {
    private UUID id;
    private boolean isDone;
    private boolean isCorrectAnswer;
    private int chooseAnswer;
}
