package ui;

import org.testfx.framework.junit5.ApplicationTest;
import core.Question;
import core.Quiz;
import io.SavePaths;
import io.QuizPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static ui.TestHelpers.deleteQuiz;

public class EditPageTest extends ApplicationTest {
    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void initQuiz(){
        clickOn("#quizNameField").write("x");
        clickOn("#addNewQuizButton");
        Quiz quiz = new Quiz("x", List.of(new Question("a", List.of("a", "a", "a", "a"), 0), new Question("b", List.of("a", "a", "a", "a"), 0)));
        try {
            QuizPersistence quizPersistence = new QuizPersistence();
            quizPersistence.saveQuiz(quiz);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
    }

    @Test
    public void testDataDisplay(){
        initQuiz();
        VBox vBox = lookup("#questionList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("a")).query();
            from(vBox).lookup((Label label) -> label.getText().equals("b")).query();
        });
        Label label = lookup("#titleText").query();
        Assertions.assertEquals("Endre x", label.getText());
        deleteQuiz("x");
    }

    @Test
    public void testAddQuestion(){
        initQuiz();
        clickOn("#newQuestionButton");
        Assertions.assertDoesNotThrow(() -> {
            lookup("#headline").query();
        });
        deleteQuiz("x");
    }
}
