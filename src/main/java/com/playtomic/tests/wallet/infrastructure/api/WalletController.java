package com.playtomic.tests.wallet.infrastructure.api;

import com.playtomic.tests.wallet.application.ObtainWallet;
import com.playtomic.tests.wallet.domain.WalletNotFound;
import com.playtomic.tests.wallet.domain.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);
    private final ObtainWallet obtainWallet;

    @Autowired
    public WalletController(ObtainWallet obtainWallet) {
        this.obtainWallet = obtainWallet;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping("/wallet/{id}")
    public WalletResponse getWallet(@PathVariable String id) {
        return new WalletResponse("", BigDecimal.ZERO);
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

