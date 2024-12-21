package com.example.picpay_desafio.transaction;

import com.example.picpay_desafio.authorization.AuthorizerService;
import com.example.picpay_desafio.exception.InvalidTransactionException;
import com.example.picpay_desafio.wallet.WalletRepository;
import com.example.picpay_desafio.wallet.WalletType;
import org.springframework.transaction.annotation.Transactional;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private  final WalletRepository walletRepository;

    private final AuthorizerService authorizerService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizerService authorizerService ) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
    }

    @Transactional // realizar a possibilidade de roolback se uma falhar vai receber o restante
    public Transaction create(Transaction transaction) {
        // 1 - validate
        validate(transaction);

        // 2 - create transaction
       var newTransaction =  transactionRepository.save(transaction);

        // 3 - debit the wallet
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4 - call external apis like notify and verify

        authorizerService.authorize(transaction);

        return newTransaction;
    }

    /*
    * - THE PAYER HAS A COMMON WALLET
    * - THE PAYER HAS ENOUGH BALANCE IN THE WALLET
    * - THE PAYER IS NOT THE PAYEE (YOU CANT SEND MONEY TO YOURSELF)
     */

    private void validate(Transaction transaction) {
        //LOGGER.info("Validating transaction {}...", transaction);

        var payee = walletRepository.findById(transaction.payee())
                .orElseThrow(() -> new InvalidTransactionException("Payee wallet not found - %s".formatted(transaction)));

        var payer = walletRepository.findById(transaction.payer())
                .orElseThrow(() -> new InvalidTransactionException("Payer wallet not found - %s".formatted(transaction)));

        if (payer.type() != WalletType.COMUM.getValue()) {
            throw new InvalidTransactionException("Payer must have a common wallet" );
        }

        if (payer.balance().compareTo(transaction.value()) < 0) {
            throw new InvalidTransactionException("Payer does not have enough balance - %s".formatted(transaction));
        }

        if (payer.id().equals(payee.id())) {
            throw new InvalidTransactionException("Payer cannot be the same as payee");
        }

        //LOGGER.info("Transaction {} is valid", transaction);
    }
}
