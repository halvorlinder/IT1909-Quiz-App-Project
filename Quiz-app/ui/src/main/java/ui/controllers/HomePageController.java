package ui.controllers;

import core.Quiz;
import io.QuizPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ui.App;
import ui.Utilities;
import javafx.scene.Node;

import java.io.IOException;


public class HomePageController {

    @FXML
    private Button startQuizButton;

    @FXML
    private Button newQuestionButton;

    @FXML
    private Button leaderboardButton;

    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    /**
     * Sets the current root to be the question page
     *
     * @throws IOException
     * @param actionEvent
     */
    @FXML
    public void showStartQuiz(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz
        QuizPersistence quizPersistence = new QuizPersistence();
        Quiz quiz = quizPersistence.loadQuiz("quiz101");
        if (quiz.getQuizLength() == 0)
            return;
        FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
        QuizController controller = new QuizController(quiz);
        loader.setController(controller);
        ((Node) actionEvent.getSource()).getScene().setRoot(loader.load());
    }

    /**
     * Sets the current root to be the new question page
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showNewQuestion(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz

        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("NewQuestion.fxml").load());
    }

    /**
     * Sets the current root to be the leaderboard page
     *
     * @throws IOException
     */
    @FXML
    public void showLeaderboard() throws IOException { // Switch scene to StartQuiz
        //newQuestionButton.getScene().setRoot(Utilities.getFXMLLoader(".fxml").load());
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("LogInPage.fxml").load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
