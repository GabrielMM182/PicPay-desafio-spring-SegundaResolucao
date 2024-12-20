package com.example.picpay_desafio.transaction;

import com.example.picpay_desafio.wallet.WalletRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private  final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }
}
