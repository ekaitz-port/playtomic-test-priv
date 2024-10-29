package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.application.ObtainWallet;
import com.playtomic.tests.wallet.application.TopUpWallet;
import com.playtomic.tests.wallet.domain.Charge;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ObtainWalletController {
    private Logger log = LoggerFactory.getLogger(ObtainWalletController.class);
    private final ObtainWallet obtainWallet;

    @Autowired
    public ObtainWalletController(ObtainWallet obtainWallet, TopUpWallet topUpWallet) {
        this.obtainWallet = obtainWallet;
    }

    @GetMapping("/wallet/{id}")
    public WalletResponse getWallet(@PathVariable String id) {
        Wallet wallet = obtainWallet.obtain(new WalletId(id));
        return new WalletResponse(wallet.idAsString(), wallet.balanceAmount());
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    public record WalletResponse(String id, BigDecimal balance) {
    }
}

