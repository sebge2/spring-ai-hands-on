package be.sgerard.springai.service.chat;

import be.sgerard.springai.model.answer.*;
import be.sgerard.springai.model.request.PromptRequest;
import be.sgerard.springai.service.answer.PromptAnswerHandler;
import be.sgerard.springai.service.audio.AudioService;
import be.sgerard.springai.service.image.ImageService;
import be.sgerard.springai.service.request.PromptRequestHandler;
import be.sgerard.springai.service.store.StoreManager;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final String TEMPLATE = """
            Use the information from the DOCUMENTS section to provide accurate answers but act as if you knew this information innately.
            If unsure, simply state that you don't know.
            DOCUMENTS:
            {documents}
            """;

    private final MistralAiChatModel chatModel;
    private final StoreManager storeManager;
    private final PromptRequestHandler<PromptRequest> requestHandler;
    private final PromptAnswerHandler<PromptAnswer> answerHandler;

    @Override
    public PromptAnswer prompt(PromptRequest prompt, String documentId, AnswerType answerType) {
        final String textPrompt = requestHandler.toTextPrompt(prompt);

        final String rawAnswer = prompt(textPrompt, documentId);

        return answerHandler.generateFinalAnswer(rawAnswer, answerType);
    }

    /**
     * Prompts the specified message over the optional document.
     */
    private String prompt(String message, String documentId) {
        return Optional.ofNullable(documentId)
                .map(docId -> doPrompt(message, docId))
                .orElseGet(() -> doPrompt(message));
    }

    private String doPrompt(String message) {
        final Prompt prompt = new Prompt(message, createChatOptions());

        return call(prompt);
    }

    private String doPrompt(String message, String documentId) {
        final List<Document> documents = storeManager.find(message, documentId);

        final String foundContent = documents.stream()
                .map(Document::getContent)
                .collect(joining(System.lineSeparator()));

        final Message systemMessage = new SystemPromptTemplate(TEMPLATE).createMessage(Map.of("documents", foundContent));
        final UserMessage userMessage = new UserMessage(message);

        final Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return call(prompt);
    }

    private MistralAiChatOptions createChatOptions() {
        return MistralAiChatOptions.builder()
                .withModel(MistralAiApi.ChatModel.LARGE.getValue())
                .withTemperature(0.5f)
                .build();
    }

    private String call(Prompt prompt) {
        return chatModel.call(prompt)
                .getResults().stream()
                .map(generation -> generation.getOutput().getContent())
                .collect(joining("/n"));
    }
}
