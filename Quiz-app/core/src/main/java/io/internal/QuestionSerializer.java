package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Question;
import io.constants.JsonKeys;

import java.io.IOException;

/**
 * This class serializes a Question object into a JSON file
 */
class QuestionSerializer extends JsonSerializer<Question> {

    @Override
    public void serialize(Question question, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField(JsonKeys.QST_TEXT, question.getQuestion());
        jsonGen.writeNumberField(JsonKeys.QST_ANSWER, question.getAnswer());
        jsonGen.writeArrayFieldStart(JsonKeys.QST_CHOICES);
        for (String choice : question.getChoices()) {
            jsonGen.writeObject(choice);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}

