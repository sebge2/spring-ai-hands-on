package be.sgerard.springai.service.answer;

import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.answer.PromptAnswer;

public interface PromptAnswerHandler<A extends PromptAnswer> {

    boolean supports(AnswerType answerType);

    A generateFinalAnswer(String rawAnswer, AnswerType answerType);
}
