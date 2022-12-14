package io;

import core.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelpers {
    public static Quiz createQuizWithTwoQuestions() {
        return new Quiz("quiz101", List.of(
                new Question("What?", List.of("a", "b", "c", "d"), 2),
                new Question("Where", List.of("1", "2", "3", "4"), 3)), "per");
    }

    static void checkQuestion(Question question1, Question question2) {
        assertEquals(question1.getQuestion(), question2.getQuestion());
        assertEquals(question1.getChoices().size(), question1.getChoices().size());
        for (int i = 0; i < question1.getChoices().size(); i++) {
            assertEquals(question1.getChoice(i), question2.getChoice(i));
        }
        assertEquals(question1.getAnswer(), question2.getAnswer());
    }

    static void checkQuiz(Quiz quiz1, Quiz quiz2) {
        assertEquals(quiz1.getName(), quiz2.getName());
        assertEquals(quiz1.getQuizLength(), quiz2.getQuizLength());
        for (int i = 0; i < quiz1.getQuizLength(); i++) {
            checkQuestion(quiz1.getQuestions().get(i), quiz2.getQuestions().get(i));
        }
    }

    static void checkUserData(UserData ud1, UserData ud2){
        assertEquals(ud1.getUserNames().size(), ud2.getUserNames().size());
        assertEquals(ud1.getUserNames(), ud2.getUserNames());
        for(String username:ud1.getUserNames()){
            assertEquals(ud1.getPasswordHash(username), ud2.getPasswordHash(username));
        }
    }

    static UserData createUserDataWithTwoEntries(){
        UserData userData = new UserData();
        UserRecord userRecord1 = new UserRecord("user1", "password");
        UserRecord userRecord2 = new UserRecord("user2", "pWord");
        userData.attemptRegister(userRecord1);
        userData.attemptRegister(userRecord2);
        return userData;
    }

    public static Leaderboard createLeaderboardWithTwoScore() {
        return new Leaderboard("test", List.of(new Score("oskar", 2),
                new Score("halvor", 0)), 2);
    }

    static void checkLeaderboard(Leaderboard leaderboard1, Leaderboard leaderboard2) {
        assertEquals(leaderboard1.getQuizName(), leaderboard2.getQuizName());
        assertEquals(leaderboard1.getScoreLength(), leaderboard2.getScoreLength());
        for (int i = 0; i < leaderboard1.getScoreLength(); i++) {
            checkScore(leaderboard1.getScores().get(i), leaderboard2.getScores().get(i));
        }
    }

    static void checkScore(Score score1, Score score2) {
        assertEquals(score1.getName(), score2.getName());
        assertEquals(score1.getPoints(), score2.getPoints());
    }

    static void checkUserRecord(UserRecord UR1, UserRecord UR2) {
        assertEquals(UR1.getUsername(), UR2.getUsername());
        assertEquals(UR1.getPassword(), UR1.getPassword());
    }
}