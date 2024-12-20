package com.example.picpay_desafio.transaction;

import com.example.picpay_desafio.wallet.WalletRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private  final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public Transaction create(Transaction transaction) {
        // 1 - validate

        // 2 - create transaction
       var newTransaction =  transactionRepository.save(transaction);

        // 3 - debit the wallet
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4 - call external apis like notify and verify

        return newTransaction;
    }
}
