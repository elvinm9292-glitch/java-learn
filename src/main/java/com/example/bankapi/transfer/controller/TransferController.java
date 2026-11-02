package com.example.bankapi.transfer.controller;

import com.example.bankapi.transfer.dto.TransferRequest;
import com.example.bankapi.transfer.dto.TransferResponse;
import com.example.bankapi.transfer.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transfers", description = "Money transfers between accounts")
@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Operation(summary = "Transfer money between two accounts")
    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        Long operationId = transferService.transfer(request.fromIban(), request.toIban(), request.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransferResponse(operationId));
    }
}
