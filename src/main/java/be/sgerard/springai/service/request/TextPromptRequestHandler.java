package be.sgerard.springai.service.request;

import be.sgerard.springai.model.request.PromptRequest;
import be.sgerard.springai.model.request.TextPromptRequest;
import org.springframework.stereotype.Component;

@Component
public class TextPromptRequestHandler implements PromptRequestHandler<TextPromptRequest> {

    @Override
    public boolean supports(PromptRequest request) {
        return request instanceof TextPromptRequest;
    }

    @Override
    public String toTextPrompt(TextPromptRequest request) {
        return request.payload();
    }
}
