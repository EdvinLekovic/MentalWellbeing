package com.example.backend.models.requests.credit_card_requests;

import com.example.backend.models.requests.user_requests.UsernameRequest;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreditCardRequest {
    @NotNull
    private String transactionalNumber;
    @NotNull
    private UsernameRequest creatorUsername;
    @NotNull
    private String cvvCode;
    @NotNull
    private Long expirationMonth;
    @NotNull
    private Long expirationYear;

    public CreditCardRequest(String transactionalNumber,
                         UsernameRequest creatorUsername,
                         String cvvCode,
                         Long expirationMonth,
                         Long expirationYear) {
        this.transactionalNumber = transactionalNumber;
        this.creatorUsername = creatorUsername;
        this.cvvCode = cvvCode;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public CreditCardRequest() {
    }
}
