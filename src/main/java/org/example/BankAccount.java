package org.example;

import java.util.UUID;

public class BankAccount {

    private final UUID accountNumber = UUID.randomUUID();
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized boolean deposit(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public synchronized boolean withdraw(int amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }
}