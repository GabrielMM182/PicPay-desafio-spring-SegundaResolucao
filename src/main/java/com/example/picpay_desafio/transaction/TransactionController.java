package com.example.picpay_desafio.transaction;

import com.example.picpay_desafio.wallet.Wallet;
import com.example.picpay_desafio.wallet.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final WalletService walletService;

    public TransactionController(TransactionService transactionService, WalletService walletService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.create(transaction);
    }

    @GetMapping
    @CrossOrigin
    public List<Transaction> list() {
        return transactionService.list();
    }

    @GetMapping("/wallets")
    @CrossOrigin
    public List<Wallet> listWallet() {
        return walletService.listWallets();
    }

}
