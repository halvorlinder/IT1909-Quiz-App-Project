package ui.controllers;

import core.Quiz;
import io.QuizPersistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import ui.App;

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
     */
    @FXML
    public void showStartQuiz() throws IOException { // Switch scene to StartQuiz
        QuizPersistence quizPersistence = new QuizPersistence();
        Quiz quiz = quizPersistence.loadQuiz("quiz101");
        System.out.println(quiz);
        if (quiz.getQuizLength() == 0)
            return;
        FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
        QuizController controller = new QuizController(quiz);
        loader.setController(controller);
        App.setRoot(loader);
    }

    /**
     * Sets the current root to be the new question page
     *
     * @throws IOException
     */
    @FXML
    public void showNewQuestion() throws IOException { // Switch scene to StartQuiz
        App.setRoot("NewQuestion.fxml");
    }

    /**
     * Sets the current root to be the leaderboard page
     *
     * @throws IOException
     */
    @FXML
    public void showLeaderboard() throws IOException { // Switch scene to StartQuiz
        App.setRoot("Leaderboard.FXML");
    }


}
