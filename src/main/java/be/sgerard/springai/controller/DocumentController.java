package be.sgerard.springai.controller;

import be.sgerard.springai.mapper.DocumentMapper;
import be.sgerard.springai.model.document.MultipartFileResource;
import be.sgerard.springai.model.dto.document.DocumentDto;
import be.sgerard.springai.service.document.DocumentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentManager documentManager;
    private final DocumentMapper mapper;

    @GetMapping
    List<DocumentDto> findAll() {
        return documentManager.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    DocumentDto upload(@RequestPart("file") MultipartFile file) {
        return mapper.mapToDto(documentManager.upload(new MultipartFileResource(file)));
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<DocumentDto> delete(@PathVariable String id) {
        return documentManager.deleteById(id)
                .map(mapper::mapToDto)
                .map(ResponseEntity::ofNullable)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
