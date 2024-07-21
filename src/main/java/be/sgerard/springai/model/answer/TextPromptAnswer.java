package be.sgerard.springai.model.answer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public record TextPromptAnswer(String text) implements PromptAnswer {

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(text());
    }
}
