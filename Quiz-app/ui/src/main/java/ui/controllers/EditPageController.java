package ui.controllers;

import core.Question;
import core.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.*;
import ui.constants.Errors;
import ui.constants.FilePaths;
import ui.constants.Style;

import java.io.IOException;

public class EditPageController extends GoBackController implements InitializableController {
    @FXML
    private Label titleText;
    @FXML
    private VBox questionList;
    @FXML
    private Button backButton;

    private final String quizName;
    private Quiz quiz;
    private APIClientService apiClientService;

    /**
     * @param quizName the name of the quiz to be edited
     * @param user     the current user
     */
    public EditPageController(String quizName, User user) {
        super(user);
        this.quizName = quizName;
    }

    /**
     * initializes the page by filling in question rows and displaying name
     *
     * @throws IOException
     */
    @Override
    @FXML
    public void initialize() throws IOException {
        apiClientService = new APIClientService();
        setBackButton(backButton);
        display();
    }

    /**
     * clears the questionList and fills it with updated list of questions
     *
     * @throws IOException
     */
    private void display() throws IOException {
        questionList.getChildren().clear();
        quiz = apiClientService.getQuiz(quizName);
        titleText.setText("Endre " + quizName);
        for (int i = 0; i < quiz.getQuizLength(); i++) {
            addQuestionElement(i);
        }
    }

    /**
     * @return the current scene
     */
    private Scene getScene() {
        return titleText.getScene();
    }

    /**
     * adds a gui element representing a question from the quiz
     *
     * @param questionId the id of the question
     */
    private void addQuestionElement(int questionId) {
        Question question = quiz.getQuestions().get(questionId);
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(Style.TABLE_SPACE_LARGE);
        ColumnConstraints column2 = new ColumnConstraints(Style.TABLE_SPACE_SMALL);
        ColumnConstraints column3 = new ColumnConstraints(Style.TABLE_SPACE_SMALL);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        Label name = new Label();
        name.setText(question.getQuestion());
        gridPane.add(name, 0, 0, 1, 1);
        gridPane.getStyleClass().add("light-grid");

        Button editButton = new Button();
        editButton.setText("Endre");
        editButton.getStyleClass().add("green-button");
        editButton.setOnAction((ActionEvent ae) -> {
            showEditQuestion(questionId, question);
        });
        gridPane.add(editButton, 1, 0, 1, 1);

        Button deleteButton = new Button();
        deleteButton.setText("Slett");
        deleteButton.getStyleClass().add("red-button");
        deleteButton.setOnAction((ActionEvent ae) -> {
            deleteQuestion(questionId);
        });
        gridPane.add(deleteButton, 2, 0, 1, 1);

        questionList.getChildren().add(gridPane);
    }

    /**
     * delete a question by id
     *
     * @param questionId the id of the question
     */
    private void deleteQuestion(int questionId) {
        try {
            apiClientService.deleteQuestion(quizName, questionId, getUser().getAccessToken());
            display();
        } catch (Exception ignored) {
        }
    }

    /**
     * renders the EditQuestion page
     *
     * @param questionId the id of the question to be edited
     * @param question   the question to be edited
     */
    private void showEditQuestion(int questionId, Question question) {
        FXMLLoader loader = null;
        try {
            loader = App.getFXMLLoader(FilePaths.NEW_QUESTION_PAGE);
            NewQuestionPageController controller =
                    new NewQuestionPageController(quizName, questionId, question, getUser());
            loader.setController(controller);
            controller.setPreviousPageInfo(this, getScene().getRoot());
        } catch (IOException ignored) {
            Utilities.alertUser(Errors.LOAD_PAGE);
            return;
        }
        try {
            Parent root = loader.load();
            getScene().setRoot(root);
        } catch (IOException ignored) {
        }
    }


    /**
     * renders the new question page
     */
    @FXML
    private void showNewQuestion() {
        FXMLLoader loader = null;
        try {
            loader = App.getFXMLLoader(FilePaths.NEW_QUESTION_PAGE);
            NewQuestionPageController controller =
                    new NewQuestionPageController(quizName, getUser());
            loader.setController(controller);
            controller.setPreviousPageInfo(this, getScene().getRoot());
        } catch (IOException ignored) {
            Utilities.alertUser(Errors.LOAD_PAGE);
            return;
        }
        try {
            Parent root = loader.load();
            getScene().setRoot(root);
        } catch (IOException ignored) {
        }
    }

    /**
     * deletes a quiz by its name
     *
     * @throws IOException
     */
    @FXML
    private void deleteQuiz() throws IOException {
        try {
            apiClientService.deleteQuiz(quizName, getUser().getAccessToken());
        } catch (Exception ignored) {
            return;
        }
        goBack();
    }

}
