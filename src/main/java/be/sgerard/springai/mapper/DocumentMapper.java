package be.sgerard.springai.mapper;

import be.sgerard.springai.model.document.DocumentEntity;
import be.sgerard.springai.model.dto.document.DocumentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto mapToDto(DocumentEntity document);
}
