package be.sgerard.springai.model.answer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public record ImagePromptAnswer(byte[] image) implements PromptAnswer {

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
