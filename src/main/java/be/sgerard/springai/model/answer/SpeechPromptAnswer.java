package be.sgerard.springai.model.answer;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static be.sgerard.springai.controller.ChatBotController.MP3_VALUE;

public record SpeechPromptAnswer(Resource resource) implements PromptAnswer {

    @Override
    public ResponseEntity<?> toResponseEntity() throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MP3_VALUE))
                .body(resource.getContentAsByteArray());
    }
}
