package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBank {

    private final ConcurrentHashMap<UUID, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(int initialBalance) {
        BankAccount account = new BankAccount(initialBalance);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public boolean transfer(BankAccount from, BankAccount to, int amount) {
        if (amount <= 0 || from == null || to == null) {
            return false;
        }
        if (from == to) {
            return true;
        }

        BankAccount lockFirst;
        BankAccount lockSecond;
        if (from.getAccountNumber().compareTo(to.getAccountNumber()) < 0) {
            lockFirst = from;
            lockSecond = to;
        } else {
            lockFirst = to;
            lockSecond = from;
        }

        synchronized (lockFirst) {
            synchronized (lockSecond) {
                if (!from.withdraw(amount)) {
                    return false;
                }
                to.deposit(amount);
                return true;
            }
        }
    }


    public int getTotalBalance() {
        List<BankAccount> snapshot = new ArrayList<>(accounts.values());
        snapshot.sort(Comparator.comparing(BankAccount::getAccountNumber));
        return sumBalancesWithOrderedLocks(snapshot, 0);
    }

    private int sumBalancesWithOrderedLocks(List<BankAccount> sorted, int index) {
        if (index >= sorted.size()) {
            return 0;
        }
        synchronized (sorted.get(index)) {
            return sorted.get(index).getBalance() + sumBalancesWithOrderedLocks(sorted, index + 1);
        }
    }
}