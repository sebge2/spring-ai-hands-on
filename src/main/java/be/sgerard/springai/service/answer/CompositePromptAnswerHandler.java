package be.sgerard.springai.service.answer;

import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.answer.PromptAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class CompositePromptAnswerHandler implements PromptAnswerHandler<PromptAnswer> {

    private final List<PromptAnswerHandler<?>> handlers;

    @Override
    public boolean supports(AnswerType answerType) {
        return handlers.stream()
                .anyMatch(handler -> handler.supports(answerType));
    }

    @Override
    @SuppressWarnings("unchecked")
    public PromptAnswer generateFinalAnswer(String rawAnswer, AnswerType answerType) {
        return handlers.stream()
                .filter(handler -> handler.supports(answerType))
                .map(handler -> ((PromptAnswerHandler<PromptAnswer>) handler))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported type [%s].".formatted(answerType)))
                .generateFinalAnswer(rawAnswer, answerType);
    }
}
