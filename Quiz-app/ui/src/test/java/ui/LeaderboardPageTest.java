package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.HomePageController;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LeaderboardPageTest extends ApplicationTest {

    private FXMLLoader loader;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        //starts the server and loads the home page
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[]")));
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        HomePageController homePageController = new HomePageController(new User("user", ""));
        loader.setController(homePageController);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //mocks getting and posting leaderboards and quizzes, and creates a quiz
    private void initQuiz() {
        stubFor(post(urlEqualTo("/api/quizzes"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"creator\":\"user\",\"questions\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(post(urlEqualTo("/api/leaderboards"))
                .withRequestBody(equalToJson("{\"name\":\"x\",\"maxScore\":0,\"scores\":[]}"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/leaderboards"))
                .willReturn(aResponse()
                        .withBody("[\"x\"]")
                        .withStatus(200)));
        clickOn("#quizNameField").write("x");
        clickOn("#addNewQuizButton");
    }

    //starts the server
    @BeforeEach
    public void startWireMockServerAndSetup() throws IOException, InterruptedException {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
    }

    //tests that the page is loaded correctly given an empty leaderboard
    @Test
    public void testInitEmptyLeaderboard() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/leaderboards/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"maxScore\":0,\"scores\":[]}")
                        .withStatus(200)));
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Ledertavle")).queryButton());
    }

    //tests that the page is loaded correctly given a non-empty leaderboard
    @Test
    public void testInitLeaderboard() {
        initQuiz();
        stubFor(get(urlEqualTo("/api/leaderboards/x"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"x\",\"maxScore\":1," +
                                "\"scores\":[{\"name\":\"oskar\",\"points\":1},{\"name\":\"halvor\",\"points\":0}]}")
                        .withStatus(200)));
        VBox qList = lookup("#quizList").query();
        clickOn(from(qList).lookup((Button b) -> b.getText().equals("Ledertavle")).queryButton());
        VBox lbList = lookup("#leaderboardList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(lbList).lookup((Label label) -> label.getText().equals("1")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("2")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("0/1")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("1/1")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("oskar")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("halvor")).query();
        });
    }

    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }
}
