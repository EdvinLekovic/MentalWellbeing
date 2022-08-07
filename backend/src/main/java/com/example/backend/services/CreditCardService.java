package com.example.backend.services;

import com.example.backend.models.CreditCard;
import com.example.backend.models.exceptions.NotEnoughMoneyOnTheCreditCardException;
import com.example.backend.models.requests.credit_card_requests.CreditCardRequest;
import com.example.backend.models.requests.credit_card_requests.CreditCardTransactionNumberRequest;
import com.example.backend.models.requests.payment_requests.PaymentRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    List<CreditCard> listAllCreditCardByUser(UsernameRequest usernameRequest);
    Optional<CreditCard> findCreditCardByCardNumber(CreditCardTransactionNumberRequest creditCardTransactionNumberRequest);
    Optional<CreditCard> addCreditCardByUser(CreditCardRequest creditCardRequest);
    void deleteCreditCard(CreditCardTransactionNumberRequest creditCardTransactionNumberRequest);
    Optional<CreditCard> buyVideo(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException;
    Optional<CreditCard> buyPodcast(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException;
    Optional<CreditCard> buyBook(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException;
    Optional<CreditCard> buyVideoCourse(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException;
}
