package be.sgerard.springai.model.answer;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public sealed interface PromptAnswer permits ImagePromptAnswer, SpeechPromptAnswer, TextPromptAnswer {

    ResponseEntity<?> toResponseEntity() throws IOException;
}
