package com.example.backend.models.requests.payment_requests;

import com.example.backend.models.requests.user_requests.UsernameRequest;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PaymentRequest {
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String transactionCardNumber;
    @NotNull
    private String cvvCode;
    @NotNull
    private UsernameRequest usernameRequest;
    @NotNull
    private Double productPrice;
    @NotNull
    private Long productId;

    public PaymentRequest(String name,
                          String lastName,
                          String transactionCardNumber,
                          String cvvCode,
                          UsernameRequest usernameRequest,
                          Double productPrice,
                          Long productId) {
        this.name = name;
        this.lastName = lastName;
        this.transactionCardNumber = transactionCardNumber;
        this.cvvCode = cvvCode;
        this.usernameRequest = usernameRequest;
        this.productPrice = productPrice;
        this.productId = productId;
    }

    public PaymentRequest() {}

}
