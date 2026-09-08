package com.sabra.wallet.exception;

public class SameWalletTransferException extends RuntimeException {
    public SameWalletTransferException(String message) {
        super(message);
    }
}
