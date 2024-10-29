package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.application.ObtainWallet;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class GetWalletController {
    private final ObtainWallet obtainWallet;

    @Autowired
    public GetWalletController(ObtainWallet obtainWallet) {
        this.obtainWallet = obtainWallet;
    }

    @GetMapping("/wallet/{id}")
    public WalletResponse get(@PathVariable String id) {
        Wallet wallet = obtainWallet.execute(new WalletId(id));
        return new WalletResponse(wallet.idAsString(), wallet.balanceAmount());
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    public record WalletResponse(String id, BigDecimal balance) {
    }
}

