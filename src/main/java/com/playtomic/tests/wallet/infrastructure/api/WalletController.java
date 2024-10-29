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

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);
    private final ObtainWallet obtainWallet;
    private final TopUpWallet topUpWallet;

    @Autowired
    public WalletController(ObtainWallet obtainWallet, TopUpWallet topUpWallet) {
        this.obtainWallet = obtainWallet;
        this.topUpWallet = topUpWallet;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping("/wallet/{id}")
    public WalletResponse getWallet(@PathVariable String id) {
        Wallet wallet = obtainWallet.obtain(new WalletId(id));
        return new WalletResponse(wallet.idAsString(), wallet.balanceAmount());
    }

    @PutMapping("/wallet/{id}/top-up")
    public void topup(@PathVariable String id, @RequestBody WalletTopUpBody topup) {
        topUpWallet.topUp(new WalletId(id), new Charge(topup.card(), topup.amount()));
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

