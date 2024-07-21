package be.sgerard.springai.service.answer;

import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.answer.TextPromptAnswer;
import org.springframework.stereotype.Component;

@Component
public class TextPromptAnswerHandler implements PromptAnswerHandler<TextPromptAnswer> {

    @Override
    public boolean supports(AnswerType answerType) {
        return answerType == AnswerType.TEXT;
    }

    @Override
    public TextPromptAnswer generateFinalAnswer(String rawAnswer, AnswerType answerType) {
        return new TextPromptAnswer(rawAnswer);
    }
}
