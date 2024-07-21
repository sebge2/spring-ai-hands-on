package be.sgerard.springai.service.request;

import be.sgerard.springai.model.request.PromptRequest;

public interface PromptRequestHandler<R extends PromptRequest> {

    boolean supports(PromptRequest request);

    String toTextPrompt(R request);
}
