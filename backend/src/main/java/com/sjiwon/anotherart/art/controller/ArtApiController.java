package com.sjiwon.anotherart.art.controller;

import com.sjiwon.anotherart.art.controller.dto.request.ArtDuplicateCheckRequest;
import com.sjiwon.anotherart.art.controller.dto.request.ArtRegisterRequest;
import com.sjiwon.anotherart.art.service.ArtService;
import com.sjiwon.anotherart.token.utils.ExtractPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/art")
public class ArtApiController {
    private final ArtService artService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerArt(@ExtractPayload Long ownerId,
                                            @ModelAttribute @Valid ArtRegisterRequest request) {
        Long savedArtId = artService.registerArt(ownerId, request);

        return ResponseEntity
                .created(UriComponentsBuilder.fromPath("/api/arts/{id}").build(savedArtId))
                .build();
    }

    @GetMapping("/check-duplicates")
    public ResponseEntity<Void> duplicateCheck(@ModelAttribute @Valid ArtDuplicateCheckRequest request) {
        artService.duplicateCheck(request.resource(), request.value());
        return ResponseEntity.noContent().build();
    }
}
