package ood;

import java.util.*;

public class BankingSystem {

    private static final long EXPIRATION_PERIOD = 86400000L;

    // Account structure
    private static class Account {
        String id;
        int balance;
        int transactionValue;
        TreeMap<Integer, Integer> balanceHistory = new TreeMap<>();

        public Account(String id, int timestamp) {
            this.id = id;
            this.balance = 0;
            this.transactionValue = 0;
            balanceHistory.put(timestamp, 0);
        }

        void updateBalance(int amount, int timestamp) {
            this.balance += amount;
            balanceHistory.put(timestamp, balance);
        }

        int getBalanceAt(int timestamp) {
            Map.Entry<Integer, Integer> entry = balanceHistory.floorEntry(timestamp);
            return entry == null ? 0 : entry.getValue();
        }
    }

    private static class Transfer {
        String id;
        String source;
        String target;
        int amount;
        int timestamp;
        boolean accepted = false;

        Transfer(String id, String source, String target, int amount, int timestamp) {
            this.id = id;
            this.source = source;
            this.target = target;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        boolean isExpired(int currentTimestamp) {
            return currentTimestamp >= (timestamp + EXPIRATION_PERIOD);
        }
    }

    // Data Stores
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Transfer> transfers = new HashMap<>();
    private final Map<String, String> mergedMap = new HashMap<>();
    private int transferCounter = 0;

    // Utils
    private String resolve(String id) {
        while (mergedMap.containsKey(id)) {
            id = mergedMap.get(id);
        }
        return id;
    }

    private Account getAccount(String id) {
        return accounts.get(resolve(id));
    }

    // Level 1
    public String createAccount(int timestamp, String accountId) {
        accountId = resolve(accountId);
        if (accounts.containsKey(accountId)) return "false";
        accounts.put(accountId, new Account(accountId, timestamp));
        return "true";
    }

    public String deposit(int timestamp, String accountId, int amount) {
        Account acc = getAccount(accountId);
        if (acc == null) return "";
        acc.updateBalance(amount, timestamp);
        acc.transactionValue += amount;
        return String.valueOf(acc.balance);
    }

    public String pay(int timestamp, String accountId, int amount) {
        Account acc = getAccount(accountId);
        if (acc == null || acc.balance < amount) return "";
        acc.updateBalance(-amount, timestamp);
        acc.transactionValue += amount;
        return String.valueOf(acc.balance);
    }

    // Level 2
    public String topActivity(int n) {
        PriorityQueue<Account> pq = new PriorityQueue<>((a, b) -> {
            int cmp = Integer.compare(b.transactionValue, a.transactionValue);
            return cmp != 0 ? cmp : a.id.compareTo(b.id);
        });

        pq.addAll(accounts.values());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n && !pq.isEmpty(); i++) {
            Account acc = pq.poll();
            result.add(acc.id + "(" + acc.transactionValue + ")");
        }

        return String.join(", ", result);
    }

    // Level 3
    public String transfer(int timestamp, String sourceId, String targetId, int amount) {
        sourceId = resolve(sourceId);
        targetId = resolve(targetId);

        if (sourceId.equals(targetId)) return "";
        Account source = accounts.get(sourceId);
        Account target = accounts.get(targetId);
        if (source == null || target == null || source.balance < amount) return "";

        source.updateBalance(-amount, timestamp);
        String transferId = "transfer" + (++transferCounter);
        Transfer t = new Transfer(transferId, sourceId, targetId, amount, timestamp);
        transfers.put(transferId, t);
        return transferId;
    }

    public String acceptTransfer(int timestamp, String accountId, String transferId) {
        Transfer t = transfers.get(transferId);
        accountId = resolve(accountId);

        if (t == null || t.accepted || t.isExpired(timestamp)) return "false";
        if (!resolve(t.target).equals(accountId)) return "false";

        Account source = accounts.get(resolve(t.source));
        Account target = accounts.get(accountId);

        target.updateBalance(t.amount, timestamp);
        source.transactionValue += t.amount;
        target.transactionValue += t.amount;
        t.accepted = true;

        return "true";
    }

    // Level 4
    public String getBalance(int timestamp, String accountId, int timeAt) {
        Account acc = getAccount(accountId);
        if (acc == null) return "null";
        return String.valueOf(acc.getBalanceAt(timeAt));
    }

    public String mergeAccounts(int timestamp, String targetId, String sourceId) {
        targetId = resolve(targetId);
        sourceId = resolve(sourceId);

        if (targetId.equals(sourceId)) return "false";
        Account target = accounts.get(targetId);
        Account source = accounts.get(sourceId);
        if (target == null || source == null) return "false";

        // Merge balances
        target.updateBalance(source.balance, timestamp);

        // Merge transaction history
        target.transactionValue += source.transactionValue;

        // Merge balance history
        for (Map.Entry<Integer, Integer> e : source.balanceHistory.entrySet()) {
            target.balanceHistory.putIfAbsent(e.getKey(), target.balance);
        }

        mergedMap.put(sourceId, targetId);
        accounts.remove(sourceId);
        return "true";
    }

    // Test
    public static void main(String[] args) {
        BankingSystem bank = new BankingSystem();

        System.out.println(bank.createAccount(1, "Alice")); // true
        System.out.println(bank.deposit(2, "Alice", 1000)); // 1000

        System.out.println(bank.createAccount(3, "Bob"));   // true
        System.out.println(bank.transfer(4, "Alice", "Bob", 300)); // transfer1
        System.out.println(bank.acceptTransfer(5, "Bob", "transfer1")); // true

        System.out.println(bank.pay(6, "Alice", 200)); // 500
        System.out.println(bank.topActivity(2)); // Alice(1500), Bob(300)

        System.out.println(bank.getBalance(7, "Bob", 5)); // 300
        System.out.println(bank.mergeAccounts(8, "Alice", "Bob")); // true
        System.out.println(bank.getBalance(9, "Alice", 5)); // 800
    }
}
