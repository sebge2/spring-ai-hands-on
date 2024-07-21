package be.sgerard.springai.model.audio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {

    FRENCH("fr"),

    ENGLISH("en");

    private final String value;
}
