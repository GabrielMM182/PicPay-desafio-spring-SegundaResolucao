package com.example.picpay_desafio.transaction;

import com.example.picpay_desafio.authorization.AuthorizerService;
import com.example.picpay_desafio.notification.NotificationService;
import com.example.picpay_desafio.wallet.WalletRepository;
import com.example.picpay_desafio.wallet.WalletType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private final AuthorizerService authorizerService;

    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository,
                              AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional // realizar a possibilidade de roolback se uma falhar vai receber o restante
    public Transaction create(Transaction transaction) {
        // 1 - validate
        validate(transaction);

        // 2 - create transaction
        var newTransaction = transactionRepository.save(transaction);

        // 3 - debit the wallet and credit
        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();

        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));


        // 4 - call external apis like notify and verify

        authorizerService.authorize(transaction);

        notificationService.notify(transaction);

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
            throw new InvalidTransactionException("Payer must have a common wallet");
        }

        if (payer.balance().compareTo(transaction.value()) < 0) {
            throw new InvalidTransactionException("Payer does not have enough balance - %s".formatted(transaction));
        }

        if (payer.id().equals(payee.id())) {
            throw new InvalidTransactionException("Payer cannot be the same as payee");
        }
    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }
}
