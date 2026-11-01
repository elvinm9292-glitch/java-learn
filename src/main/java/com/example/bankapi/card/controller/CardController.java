package com.example.bankapi.card.controller;

import com.example.bankapi.card.dto.IssueCardRequest;
import com.example.bankapi.card.dto.IssuedCardResponse;
import com.example.bankapi.card.entity.Card;
import com.example.bankapi.card.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cards", description = "Card operations")
@RestController
@RequestMapping("api/card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @Operation(summary = "Issue a new card")
    @PostMapping
    public ResponseEntity<IssuedCardResponse> issue(@Valid @RequestBody IssueCardRequest request){
        Card card = cardService.issue(request.iban(), request.tier());
        var body = new IssuedCardResponse(card.getId(), card.getNumber(), card.getCvv(),
                card.getExpiry(),card.getStatus(), card.getTier(), card.getAccount().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
