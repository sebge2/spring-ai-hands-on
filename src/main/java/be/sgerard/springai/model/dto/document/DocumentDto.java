package be.sgerard.springai.model.dto.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class DocumentDto {

    private final String id;

    private final String fileName;

    private final int numberPages;
}
