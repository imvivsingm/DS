package ood;

import com.sun.source.tree.Tree;

import java.util.*;

public class InMemoryDb {
    static class FieldValue {
        int value;
        Integer expireTime; // null = permanent

        FieldValue(int value, Integer expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }

        boolean isExpired(int currentTime) {
            return expireTime != null && currentTime >= expireTime;
        }

        public int getRemainingTTL(int now) {
            return expireTime == null ? Integer.MAX_VALUE : Math.max(0, expireTime - now);
        }

        FieldValue cloneWithNewExpireTime(int newExpireTime) {
            return new FieldValue(this.value, newExpireTime == Integer.MAX_VALUE ? null : newExpireTime);
        }
    }

    // Snapshot class
    static class Snapshot {
        int backupTime;
        Map<String, TreeMap<String, FieldValue>> data;

        Snapshot(int backupTime, Map<String, TreeMap<String, FieldValue>> data) {
            this.backupTime = backupTime;
            this.data = data;
        }
    }

    private Map<String, TreeMap<String, FieldValue>> db;
    private TreeMap<Integer, Snapshot> backups; // timestamp -> snapshot


    public InMemoryDb() {
        this.db = new HashMap<>();
        this.backups = new TreeMap<>();
    }

    // -- Set without TTL (clears any TTL)
    public void set(int timestamp, String key, String field, int value) {
        db.computeIfAbsent(key, k -> new TreeMap<>())
                .put(field, new FieldValue(value, null));
    }

    // -- Set with TTL
    public void setWithTTL(int timestamp, String key, String field, int value, int ttl) {
        int expireTime = timestamp + ttl;
        db.computeIfAbsent(key, k -> new TreeMap<>())
                .put(field, new FieldValue(value, expireTime));
    }

    // -- Get with TTL check
    public Integer get(int timestamp, String key, String field) {
        if (!db.containsKey(key)) return null;
        FieldValue fv = db.get(key).get(field);
        if (fv == null || fv.isExpired(timestamp)) return null;
        return fv.value;
    }

    // -- Compare and Set (no TTL)
    public boolean compareAndSet(int timestamp, String key, String field, int expectedValue, int newValue) {
        if (!db.containsKey(key)) return false;
        FieldValue fv = db.get(key).get(field);
        if (fv == null || fv.isExpired(timestamp)) return false;

        if (fv.value == expectedValue) {
            db.get(key).put(field, new FieldValue(newValue, null)); // remove TTL
            return true;
        }
        return false;
    }

    // -- Compare and Delete
    public boolean compareAndDelete(int timestamp, String key, String field, int expectedValue) {
        if (!db.containsKey(key)) return false;
        FieldValue fv = db.get(key).get(field);
        if (fv == null || fv.isExpired(timestamp)) return false;

        if (fv.value == expectedValue) {
            db.get(key).remove(field);
            return true;
        }
        return false;
    }

    // -- Compare and Set with TTL
    public boolean compareAndSetWithTTL(int timestamp, String key, String field, int expectedValue, int newValue, int ttl) {
        if (!db.containsKey(key)) return false;
        FieldValue fv = db.get(key).get(field);
        if (fv == null || fv.isExpired(timestamp)) return false;

        if (fv.value == expectedValue) {
            int expireTime = timestamp + ttl;
            db.get(key).put(field, new FieldValue(newValue, expireTime));
            return true;
        }
        return false;
    }

    // -- Scan (only unexpired fields)
    public List<String> scan(int timestamp, String key) {
        List<String> result = new ArrayList<>();
        if (!db.containsKey(key)) return result;

        for (Map.Entry<String, FieldValue> entry : db.get(key).entrySet()) {
            FieldValue fv = entry.getValue();
            if (!fv.isExpired(timestamp)) {
                result.add(entry.getKey() + "(" + fv.value + ")");
            }
        }
        return result;
    }

    // -- Scan by prefix (only unexpired fields)
    public List<String> scanByPrefix(int timestamp, String key, String prefix) {
        List<String> result = new ArrayList<>();
        if (!db.containsKey(key)) return result;

        for (Map.Entry<String, FieldValue> entry : db.get(key).entrySet()) {
            String field = entry.getKey();
            FieldValue fv = entry.getValue();
            if (field.startsWith(prefix) && !fv.isExpired(timestamp)) {
                result.add(field + "(" + fv.value + ")");
            }
        }
        return result;
    }

    // === Level 4: BACKUP ===
    public String backup(int timestamp) {
        Map<String, TreeMap<String, FieldValue>> snapshot = new HashMap<>();

        for (Map.Entry<String, TreeMap<String, FieldValue>> entry : db.entrySet()) {
            TreeMap<String, FieldValue> record = entry.getValue();
            TreeMap<String, FieldValue> clonedRecord = new TreeMap<>();

            for (Map.Entry<String, FieldValue> fieldEntry : record.entrySet()) {
                FieldValue fv = fieldEntry.getValue();
                if (!fv.isExpired(timestamp)) {
                    int remainingTTL = fv.getRemainingTTL(timestamp);
                    clonedRecord.put(fieldEntry.getKey(),
                            new FieldValue(fv.value, timestamp + remainingTTL));
                }
            }

            if (!clonedRecord.isEmpty()) {
                snapshot.put(entry.getKey(), clonedRecord);
            }
        }

        backups.put(timestamp, new Snapshot(timestamp, snapshot));
        return String.valueOf(snapshot.size()); // number of non-empty records
    }

    public void restore(int timestamp, int timestampToRestore) {
        Map.Entry<Integer, Snapshot> entry = backups.floorEntry(timestampToRestore);
        if (entry == null) return;

        Snapshot snapshot = entry.getValue();
        Map<String, TreeMap<String, FieldValue>> restored = new HashMap<>();

        for (Map.Entry<String, TreeMap<String, FieldValue>> rec : snapshot.data.entrySet()) {
            TreeMap<String, FieldValue> recordCopy = new TreeMap<>();
            for (Map.Entry<String, FieldValue> field : rec.getValue().entrySet()) {
                FieldValue oldFV = field.getValue();
                Integer remainingTTL = oldFV.getRemainingTTL(snapshot.backupTime);
                Integer newExpireTime = (remainingTTL == Integer.MAX_VALUE) ? null : timestampToRestore + remainingTTL;
                recordCopy.put(field.getKey(), new FieldValue(oldFV.value, newExpireTime));
            }
            restored.put(rec.getKey(), recordCopy);
        }
        this.db = restored;
    }

    public static void main(String[] args) {
        InMemoryDb db = new InMemoryDb();

        db.setWithTTL(100, "user1", "token", 999, 10); // expires at 110
        db.set(101, "user1", "age", 25);

        System.out.println(db.get(105, "user1", "token")); // 999

        System.out.println("Backup count: " + db.backup(106)); // 1

        db.set(107, "user2", "level", 5);
        db.set(108, "user1", "token", 888); // overwrites and becomes permanent

        db.restore(120, 106); // restore state at time 106

        System.out.println(db.get(105, "user1", "token")); // 999 (restored)
        System.out.println(db.get(115, "user1", "token"));
    }
}
