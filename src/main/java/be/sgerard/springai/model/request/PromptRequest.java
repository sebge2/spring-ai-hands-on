package be.sgerard.springai.model.request;

public sealed interface PromptRequest permits TextPromptRequest, SpeechPromptRequest {
}
