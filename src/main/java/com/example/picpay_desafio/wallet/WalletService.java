package com.example.picpay_desafio.wallet;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Retorna todas as carteiras
    public List<Wallet> listWallets() {
        return (List<Wallet>) walletRepository.findAll();
    }
}
