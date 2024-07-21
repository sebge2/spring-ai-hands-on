package be.sgerard.springai.model.request;

import be.sgerard.springai.model.audio.Language;
import org.springframework.core.io.Resource;

public record SpeechPromptRequest(Resource payload,
                                  Language language) implements PromptRequest {
}
