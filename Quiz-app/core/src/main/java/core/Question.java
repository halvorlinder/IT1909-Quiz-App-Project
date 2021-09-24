package core;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String question;
    private final List<String> choices;
    private final int answer;

    /**
     *
     * @param question a string representing the question text
     * @param choices a list a strings representing the choices for the question
     * @param answer an int corresponding to the index of the correct answer
     */
    public Question(String question, List<String> choices, int answer){
        if (answer>=choices.size() || answer<0)
            throw new IllegalArgumentException("The answer must map to a choice");
        this.question = question;
        this.choices = new ArrayList<>(choices);
        this.answer = answer;
    }

    /**
     *
     * @param answer the index of the answer to be checked for correctness
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrect(int answer){
        if (answer<0 || answer>=choices.size())
            throw new IllegalArgumentException("The choice does not exist");
        return answer==this.answer;
    }

    /**
     *
     * @return the string representing the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param n the index of the choice
     * @return the string corresponding to the choice with index n
     */
    public String getChoice(int n) {
        if (n<0 || n>=choices.size())
            throw new IllegalArgumentException("The choice does not exist");
        return choices.get(n);
    }
}