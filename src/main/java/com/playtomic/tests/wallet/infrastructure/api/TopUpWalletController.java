package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.application.TopUpWallet;
import com.playtomic.tests.wallet.domain.Charge;
import com.playtomic.tests.wallet.domain.TopUpAmountLessThanMinimum;
import com.playtomic.tests.wallet.domain.WalletId;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TopUpWalletController {
    private final TopUpWallet topUpWallet;

    @Autowired
    public TopUpWalletController(TopUpWallet topUpWallet) {
        this.topUpWallet = topUpWallet;
    }

    @PutMapping("/wallet/{id}/top-up")
    public void topUp(@PathVariable String id, @RequestBody WalletTopUpBody topup) {
        topUpWallet.execute(new WalletId(id), new Charge(topup.card(), topup.amount()));
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopUpAmountLessThanMinimum.class)
    public ResponseEntity<Object> handleTopUpAmountLessThanMinimum(TopUpAmountLessThanMinimum e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public record WalletTopUpBody(String card, BigDecimal amount) {
    }
}

