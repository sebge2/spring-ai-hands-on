package be.sgerard.springai.model.answer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static be.sgerard.springai.controller.ChatBotController.MP3_VALUE;
import static org.apache.poi.openxml4j.opc.ContentTypes.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RequiredArgsConstructor
@Getter
public enum AnswerType {

    TEXT(TEXT_PLAIN_VALUE),

    AUDIO(MP3_VALUE),

    IMAGE(IMAGE_PNG);

    private final String mediaType;

    public static AnswerType resolve(String mediaType) {
        return Optional
                .ofNullable(mediaType)
                .map(mt -> Stream.of(values())
                        .filter(type -> Objects.equals(type.getMediaType(), mt))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Unexpected value: [%s].".formatted(mt)))
                )
                .orElse(AnswerType.TEXT);
    }
}
