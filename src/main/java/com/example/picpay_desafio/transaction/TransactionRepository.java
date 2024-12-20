package com.example.picpay_desafio.transaction;

import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository  extends ListCrudRepository<Transaction, Long> {
}
