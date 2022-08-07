package com.example.backend.repositories;

import com.example.backend.models.CreditCard;
import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard,String> {
    List<CreditCard> findAllByUser(User user);
}
