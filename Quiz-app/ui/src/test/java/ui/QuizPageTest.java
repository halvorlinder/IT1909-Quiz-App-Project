package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.QuizPageController;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class QuizPageTest extends ApplicationTest {

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;
    private final String username = "test";

    @Override
    public void start(final Stage stage) throws Exception {
        //starts the server and loads the quiz page
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"creator\":\"test\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}]}")
                        .withStatus(200)));

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizPage.fxml"));
        QuizPageController controller = new QuizPageController("x", new User(username, ""));
        loader.setController(controller);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //starts the server and mocks posting scores
    @BeforeEach
    public void startServer() {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(post(urlEqualTo("/api/leaderboards/x"))
                .withRequestBody(equalToJson("{\"name\":\"" + username + "\",\"points\":1}"))
                .willReturn(aResponse()
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/leaderboards/x"))
                .withRequestBody(equalToJson("{\"name\":\"" + username + "\",\"points\":0}"))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    //test that the score is updated correctly given a correct answer, that the request
    //is sent correctly and that the ui is updated correctly given the response
    @Test
    public void testAnswerQuizCorrect() {
        clickOn("#option" + 1);
        clickOn("#submitAnswer");
        assertDoesNotThrow(() -> lookup((Label t) -> t.getText().startsWith("Du fikk 1/1 poeng!")).query());
    }

    //test that the score is updated correctly given a wrong answer, that the request
    //is sent correctly and that the ui is updated correctly given the response
    @Test
    public void testAnswerQuizWrong() {
        clickOn("#option" + 2);
        clickOn("#submitAnswer");
        assertDoesNotThrow(() -> lookup((Label t) -> t.getText().startsWith("Du fikk 0/1 poeng!")).query());
    }

    //stops the server
    @AfterEach
    public void stopServer() {
        if (wireMockServer != null)
            wireMockServer.stop();
    }

}
