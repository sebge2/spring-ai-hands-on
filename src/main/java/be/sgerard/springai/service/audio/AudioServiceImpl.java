package be.sgerard.springai.service.audio;

import be.sgerard.springai.model.audio.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {

    private final OpenAiAudioTranscriptionModel openAiTranscriptionModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    @Override
    public String speechToText(Resource audioFile, Language language) {
        final AudioTranscriptionPrompt prompt = initializePrompt(audioFile, language);

        return openAiTranscriptionModel
                .call(prompt)
                .getResult()
                .getOutput();
    }

    @Override
    public Resource textToSpeech(String text) {
        final SpeechPrompt speechPrompt = initializePrompt(text);

        final SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);

        return new ByteArrayResource(response.getResult().getOutput());
    }

    private AudioTranscriptionPrompt initializePrompt(Resource audioFile, Language language) {
        return new AudioTranscriptionPrompt(
                audioFile,
                OpenAiAudioTranscriptionOptions.builder()
                        .withLanguage(language.getValue())
                        .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                        .withTemperature(0f)
                        .build()
        );
    }

    private SpeechPrompt initializePrompt(String text) {
        return new SpeechPrompt(
                text,
                OpenAiAudioSpeechOptions.builder()
                        .withModel("tts-1")
                        .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                        .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                        .withSpeed(1.0f)
                        .build()
        );
    }
}
