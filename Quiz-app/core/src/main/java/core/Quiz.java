package core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a quiz with a list of questions
 */
public class Quiz {

    private String name;
    private final List<Question> questions;
    private final String creator;

    /**
     * @param name      the quiz name
     * @param questions a list of question objects
     * @param creator   the creator of the quiz
     */
    public Quiz(String name, List<Question> questions, String creator) {
        if (name.isEmpty())
            throw new IllegalArgumentException("The quiz must have a non-empty name");
        if (creator.isEmpty())
            throw new IllegalArgumentException("The creator of the quiz must have a non-empty name");
        this.name = name;
        this.questions = new ArrayList<>(questions);
        this.creator = creator;
    }

    /**
     * gets a question from the quiz
     *
     * @param questionIndex the index of the question
     * @return the question at a given index
     */
    public Question getQuestion(int questionIndex) {
        if (questionIndex >= getQuizLength() || questionIndex < 0)
            throw new IllegalArgumentException("Question index must be between 0 and " + (getQuizLength() - 1));
        return questions.get(questionIndex);
    }

    /**
     * @return a copy of the questions in the quiz
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * adds a question object to the quiz
     *
     * @param question the question to be added
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }

    /**
     * delete a question from the quiz
     *
     * @param questionIndex the index of the question to be removed
     */
    public void deleteQuestion(int questionIndex) {
        if (questionIndex >= getQuizLength() || questionIndex < 0)
            throw new IllegalArgumentException("Question index must be between 0 and " + (getQuizLength() - 1));
        questions.remove(questionIndex);
    }

    /**
     * sets a new question at a given index
     *
     * @param questionIndex the index of the question
     * @param question   the new question
     */
    public void setQuestion(int questionIndex, Question question) {
        if (questionIndex >= getQuizLength() || questionIndex < 0)
            throw new IllegalArgumentException("Question index must be between 0 and " + (getQuizLength() - 1));
        questions.set(questionIndex, question);
    }

    /**
     * @return the length of the quiz
     */
    public int getQuizLength() {
        return questions.size();
    }

    /**
     * @return the creator of the quiz
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @return string representation
     */
    @Override
    public String toString() {
        return "Quiz{" +
                ", questions=" + questions +
                '}';
    }

    /**
     * @return the name of the quiz
     */
    public String getName() {
        return name;
    }

    /**
     * strips and replaces spaces with $ in the quiz name in
     * order for it to be sent over http
     */
    public void legalizeName() {
        name = name.strip().replaceAll(" ", "$");
    }
}
