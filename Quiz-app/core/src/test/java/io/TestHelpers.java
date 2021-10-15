package io;

import core.Question;
import core.Quiz;
import core.User;
import core.UserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelpers {
    public static Quiz createQuizWithTwoQuestions() {
        return new Quiz("quiz101", List.of(
                new Question("What?", List.of("a", "b", "c", "d"), 2),
                new Question("Where", List.of("1", "2", "3", "4"), 3)));
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
        userData.attemptRegister("user1", "password");
        userData.attemptRegister("user2", "pWord");
        return userData;
    }
}