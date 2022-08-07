package com.example.backend.controllers;

import com.example.backend.models.CreditCard;
import com.example.backend.models.exceptions.NotEnoughMoneyOnTheCreditCardException;
import com.example.backend.models.requests.credit_card_requests.CreditCardRequest;
import com.example.backend.models.requests.credit_card_requests.CreditCardTransactionNumberRequest;
import com.example.backend.models.requests.payment_requests.PaymentRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.services.CreditCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("http://localhost:4200")
public class PaymentController {

    private final CreditCardService creditCardService;

    public PaymentController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public List<CreditCard> findAllCreditCardsByUser(@RequestBody UsernameRequest usernameRequest) {
        return creditCardService.listAllCreditCardByUser(usernameRequest);
    }

    @PostMapping("/get-credit-card-by-number")
    public Optional<CreditCard> findCreditCardByCardNumber(@RequestBody CreditCardTransactionNumberRequest creditCardTransactionNumberRequest) {
        return creditCardService.findCreditCardByCardNumber(creditCardTransactionNumberRequest);
    }

    @PostMapping("/buy-video")
    public Optional<CreditCard> buyVideoWithCreditCard(@RequestBody PaymentRequest paymentRequest)
            throws NotEnoughMoneyOnTheCreditCardException {
        return creditCardService.buyVideo(paymentRequest);
    }

    @PostMapping("/buy-course")
    public Optional<CreditCard> buyVideoCourseWithCreditCard(@RequestBody PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException{
        return creditCardService.buyVideoCourse(paymentRequest);
    }

    @PostMapping("/buy-podcast")
    public Optional<CreditCard> buyPodcastWithCreditCard(@RequestBody PaymentRequest paymentRequest)
            throws NotEnoughMoneyOnTheCreditCardException {
        return creditCardService.buyPodcast(paymentRequest);
    }

    @PostMapping("/buy-book")
    public Optional<CreditCard> buyBookWithCreditCard(@RequestBody PaymentRequest paymentRequest)
            throws NotEnoughMoneyOnTheCreditCardException {
        return creditCardService.buyBook(paymentRequest);
    }

    @PostMapping("/add-credit-card-by-user")
    public Optional<CreditCard> addCreditCardByUser(@RequestBody CreditCardRequest creditCardRequest){
        return creditCardService.addCreditCardByUser(creditCardRequest);
    }

    @PostMapping("/delete-credit-card")
    public void deleteCreditCard(@RequestBody CreditCardTransactionNumberRequest creditCardTransactionNumberRequest){
        creditCardService.deleteCreditCard(creditCardTransactionNumberRequest);
    }
}
