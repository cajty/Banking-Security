package org.ably.bankingsecurity.exception.transaction;



public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
