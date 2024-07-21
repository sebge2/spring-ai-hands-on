package be.sgerard.springai.service.request;

import be.sgerard.springai.model.request.PromptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class CompositePromptRequestHandler implements PromptRequestHandler<PromptRequest> {

    private final List<PromptRequestHandler<?>> handlers;

    @Override
    public boolean supports(PromptRequest request) {
        return handlers.stream().anyMatch(h -> h.supports(request));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String toTextPrompt(PromptRequest request) {
        return handlers.stream()
                .filter(handler -> handler.supports(request))
                .map(handler -> (PromptRequestHandler<PromptRequest>) handler)
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported request [%s] request.".formatted(request)))
                .toTextPrompt(request);
    }
}
