package com.example.backend.services.impl;

import com.example.backend.models.*;
import com.example.backend.models.exceptions.NotEnoughMoneyOnTheCreditCardException;
import com.example.backend.models.requests.credit_card_requests.CreditCardRequest;
import com.example.backend.models.requests.credit_card_requests.CreditCardTransactionNumberRequest;
import com.example.backend.models.requests.payment_requests.PaymentRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.repositories.*;
import com.example.backend.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final UserService authService;
    private final VideoCourseRepository videoCourseRepository;
    private final VideoRepository videoRepository;
    private final PodcastRepository podcastRepository;
    private final BookRepository bookRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository,
                                 UserService authService,
                                 VideoCourseRepository videoCourseRepository,
                                 VideoRepository videoRepository,
                                 PodcastRepository podcastRepository, BookRepository bookRepository) {
        this.creditCardRepository = creditCardRepository;
        this.authService = authService;
        this.videoCourseRepository = videoCourseRepository;
        this.videoRepository = videoRepository;
        this.podcastRepository = podcastRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public List<CreditCard> listAllCreditCardByUser(UsernameRequest usernameRequest) {
        User user = authService.findUserByUsername(usernameRequest.getUsername()).orElseThrow();
        return creditCardRepository.findAllByUser(user);
    }

    @Override
    public Optional<CreditCard> findCreditCardByCardNumber(CreditCardTransactionNumberRequest creditCardTransactionNumberRequest) {
        return creditCardRepository.findById(creditCardTransactionNumberRequest.getCardNumber());
    }

    @Override
    public Optional<CreditCard> addCreditCardByUser(CreditCardRequest creditCardRequest) {
        User user = authService.findUserByUsername(creditCardRequest.getCreatorUsername().getUsername()).orElseThrow();
        double max = 1000000;
        double min = 500000;
        Double random_money = Math.floor(Math.random() * (max - min + 1) + min);
        return Optional.of(creditCardRepository.save(new CreditCard(creditCardRequest.getTransactionalNumber(),
                user,random_money,
                creditCardRequest.getCvvCode(),
                creditCardRequest.getExpirationMonth(),
                creditCardRequest.getExpirationYear())));
    }

    @Override
    public void deleteCreditCard(CreditCardTransactionNumberRequest creditCardTransactionNumberRequest) {
        creditCardRepository.deleteById(creditCardTransactionNumberRequest.getCardNumber());
    }

    @Override
    public Optional<CreditCard> buyVideo(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException {
        CreditCard creditCard = creditCardRepository.findById(paymentRequest.getTransactionCardNumber()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        if ((creditCard.getExpirationYear() > now.getYear() || creditCard.getExpirationMonth() >= now.getMonth().getValue()) &&
                creditCard.getCvvCode().equals(paymentRequest.getCvvCode()) &&
                creditCard.getMoney() >= paymentRequest.getProductPrice()) {
            creditCard.setMoney(creditCard.getMoney() - paymentRequest.getProductPrice());
            Video video = videoRepository.findById(paymentRequest.getProductId()).orElseThrow();
            User user = authService.findUserByUsername(paymentRequest.getUsernameRequest().getUsername()).orElseThrow();
            video.getUsers().add(user);
            videoRepository.save(video);
            return Optional.of(creditCardRepository.save(creditCard));
        } else {
            throw new NotEnoughMoneyOnTheCreditCardException();
        }
    }

    @Override
    public Optional<CreditCard> buyPodcast(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException {
        CreditCard creditCard = creditCardRepository.findById(paymentRequest.getTransactionCardNumber()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        if ((creditCard.getExpirationYear() > now.getYear() || creditCard.getExpirationMonth() >= now.getMonth().getValue()) &&
                creditCard.getCvvCode().equals(paymentRequest.getCvvCode()) &&
                creditCard.getMoney() >= paymentRequest.getProductPrice()) {
            creditCard.setMoney(creditCard.getMoney() - paymentRequest.getProductPrice());
            Podcast podcast = podcastRepository.findById(paymentRequest.getProductId()).orElseThrow();
            User user = authService.findUserByUsername(paymentRequest.getUsernameRequest().getUsername()).orElseThrow();
            podcast.getUsers().add(user);
            podcastRepository.save(podcast);
            return Optional.of(creditCardRepository.save(creditCard));
        } else {
            throw new NotEnoughMoneyOnTheCreditCardException();
        }
    }

    @Override
    public Optional<CreditCard> buyBook(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException {
        CreditCard creditCard = creditCardRepository.findById(paymentRequest.getTransactionCardNumber()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        if ((creditCard.getExpirationYear() > now.getYear() || creditCard.getExpirationMonth() >= now.getMonth().getValue()) &&
                creditCard.getCvvCode().equals(paymentRequest.getCvvCode()) &&
                creditCard.getMoney() >= paymentRequest.getProductPrice()) {
            creditCard.setMoney(creditCard.getMoney() - paymentRequest.getProductPrice());
            Book book = bookRepository.findById(paymentRequest.getProductId()).orElseThrow();
            User user = authService.findUserByUsername(paymentRequest.getUsernameRequest().getUsername()).orElseThrow();
            book.getUsers().add(user);
            bookRepository.save(book);
            return Optional.of(creditCardRepository.save(creditCard));
        } else {
            throw new NotEnoughMoneyOnTheCreditCardException();
        }
    }

    @Override
    public Optional<CreditCard> buyVideoCourse(PaymentRequest paymentRequest) throws NotEnoughMoneyOnTheCreditCardException {
        CreditCard creditCard = creditCardRepository.findById(paymentRequest.getTransactionCardNumber()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        if ((creditCard.getExpirationYear() > now.getYear() || creditCard.getExpirationMonth() >= now.getMonth().getValue()) &&
                creditCard.getCvvCode().equals(paymentRequest.getCvvCode()) &&
                creditCard.getMoney() >= paymentRequest.getProductPrice()) {
            creditCard.setMoney(creditCard.getMoney() - paymentRequest.getProductPrice());
            VideoCourse videoCourse = videoCourseRepository.findById(paymentRequest.getProductId()).orElseThrow();
            User user = authService.findUserByUsername(paymentRequest.getUsernameRequest().getUsername()).orElseThrow();
            videoCourse.getUsers().add(user);
            videoCourseRepository.save(videoCourse);
            return Optional.of(creditCardRepository.save(creditCard));
        } else {
            throw new NotEnoughMoneyOnTheCreditCardException();
        }
    }
}
