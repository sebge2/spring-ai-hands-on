package be.sgerard.springai.service.answer;

import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.answer.SpeechPromptAnswer;
import be.sgerard.springai.service.audio.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeechPromptAnswerHandler implements PromptAnswerHandler<SpeechPromptAnswer> {

    private final AudioService audioService;

    @Override
    public boolean supports(AnswerType answerType) {
        return answerType == AnswerType.AUDIO;
    }

    @Override
    public SpeechPromptAnswer generateFinalAnswer(String rawAnswer, AnswerType answerType) {
        return new SpeechPromptAnswer(
                audioService.textToSpeech(rawAnswer)
        );
    }
}
