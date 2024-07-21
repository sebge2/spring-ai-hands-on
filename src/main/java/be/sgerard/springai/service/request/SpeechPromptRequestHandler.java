package be.sgerard.springai.service.request;

import be.sgerard.springai.model.request.PromptRequest;
import be.sgerard.springai.model.request.SpeechPromptRequest;
import be.sgerard.springai.service.audio.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeechPromptRequestHandler implements PromptRequestHandler<SpeechPromptRequest>  {

    private final AudioService audioService;

    @Override
    public boolean supports(PromptRequest request) {
        return request instanceof SpeechPromptRequest;
    }

    @Override
    public String toTextPrompt(SpeechPromptRequest request) {
        return audioService.speechToText(request.payload(), request.language());
    }
}
