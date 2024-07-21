package be.sgerard.springai.service.answer;

import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.answer.ImagePromptAnswer;
import be.sgerard.springai.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImagePromptAnswerHandler implements PromptAnswerHandler<ImagePromptAnswer> {

    private final ImageService imageService;

    @Override
    public boolean supports(AnswerType answerType) {
        return answerType == AnswerType.IMAGE;
    }

    @Override
    public ImagePromptAnswer generateFinalAnswer(String rawAnswer, AnswerType answerType) {
        return new ImagePromptAnswer(
                imageService.textToImage(rawAnswer)
        );
    }
}
