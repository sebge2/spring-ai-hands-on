package be.sgerard.springai.controller;

import be.sgerard.springai.model.audio.Language;
import be.sgerard.springai.model.answer.AnswerType;
import be.sgerard.springai.model.request.SpeechPromptRequest;
import be.sgerard.springai.model.request.TextPromptRequest;
import be.sgerard.springai.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static be.sgerard.springai.model.answer.AnswerType.resolve;
import static org.apache.poi.openxml4j.opc.ContentTypes.IMAGE_PNG;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequestMapping("/v1/chat-bot")
@Controller
@RequiredArgsConstructor
public class ChatBotController {

    public static final String MP3_VALUE = "audio/mpeg3";

    private final ChatService chatService;

    @PostMapping(value = "/do-chat",
            consumes = {MULTIPART_FORM_DATA_VALUE},
            produces = {TEXT_PLAIN_VALUE, MP3_VALUE, IMAGE_PNG}
    )
    @Operation
    ResponseEntity<?> chat(HttpServletRequest request,
                           @RequestBody MultipartFile body,
                           @RequestParam(value = "language", defaultValue = "FRENCH") @Parameter Language language,
                           @RequestParam(value = "documentId", required = false) String documentId) throws IOException {
        return chatService
                .prompt(
                        new SpeechPromptRequest(body.getResource(), language),
                        documentId,
                        resolveResponseType(request)
                )
                .toResponseEntity();
    }

    @PostMapping(value = "/do-chat",
            consumes = {TEXT_PLAIN_VALUE},
            produces = {TEXT_PLAIN_VALUE, MP3_VALUE, IMAGE_PNG}
    )
    @Operation
    ResponseEntity<?> chat(HttpServletRequest request,
                           @RequestBody String text,
                           @RequestParam(value = "documentId", required = false) String documentId) throws IOException {
        return chatService
                .prompt(
                        new TextPromptRequest(text),
                        documentId,
                        resolveResponseType(request)
                )
                .toResponseEntity();
    }

    private AnswerType resolveResponseType(HttpServletRequest request) {
        return resolve(request.getHeader(HttpHeaders.ACCEPT));
    }
}
