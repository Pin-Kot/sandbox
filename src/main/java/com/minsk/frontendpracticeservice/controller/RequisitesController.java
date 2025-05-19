package com.minsk.frontendpracticeservice.controller;

import com.minsk.frontendpracticeservice.domain.request.RequisitesCreateRequestDto;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesCreateResponseDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requisites")
@RequiredArgsConstructor
public class RequisitesController {

    private final RequisitesService requisitesService;

    @PostMapping
    public ResponseEntity<RequisitesCreateResponseDto> create(@Valid @RequestBody RequisitesCreateRequestDto requisitesCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requisitesService.createRequisites(requisitesCreateRequestDto));
    }

    @GetMapping("/{requisitesId}")
    public ResponseEntity<RequisitesResponseDto> read(@PathVariable @UUID String requisitesId) {
        return ResponseEntity.ok(requisitesService.readRequisites(requisitesId));
    }

    @PutMapping
    public ResponseEntity<RequisitesResponseDto> update(@Valid @RequestBody RequisitesRequestUpdateDto requisitesRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(requisitesService.updateRequisites(requisitesRequestDto));
    }

    @DeleteMapping("/{requisitesId}")
    public ResponseEntity<Void> delete(@PathVariable @UUID String requisitesId) {
        requisitesService.deleteRequisites(requisitesId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
