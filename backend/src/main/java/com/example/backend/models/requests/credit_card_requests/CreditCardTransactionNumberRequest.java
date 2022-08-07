package com.example.backend.models.requests.credit_card_requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreditCardTransactionNumberRequest {
    @NotNull
    private String cardNumber;

    public CreditCardTransactionNumberRequest(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CreditCardTransactionNumberRequest() {
    }
}
