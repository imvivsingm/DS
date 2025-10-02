package ood;

import java.util.*;

public class CloudStorage {
    // File system
    private Map<String, Integer> fileStorage = new HashMap<>();                 // fileName -> size
    private Map<String, String> fileOwnership = new HashMap<>();               // fileName -> userId

    // User system
    private Map<String, Integer> userCapacity = new HashMap<>();               // userId -> total capacity
    private Map<String, Integer> userUsedStorage = new HashMap<>();            // userId -> used capacity
    private Map<String, Set<String>> userFiles = new HashMap<>();              // userId -> set of file names

    // Backup system
    private Map<String, Map<String, Integer>> userBackups = new HashMap<>();   // userId -> (fileName -> size)

    public CloudStorage() {
        userCapacity.put("admin", Integer.MAX_VALUE);
        userUsedStorage.put("admin", 0);
        userFiles.put("admin", new HashSet<>());
    }

    // Level 1: Add file (admin)
    public String addFile(String fileName, int size) {
        return addFileByUser("admin", fileName, size);
    }

    public String getFileSize(String fileName) {
        return fileStorage.containsKey(fileName) ? String.valueOf(fileStorage.get(fileName)) : "";
    }

    public String deleteFile(String fileName) {
        if (!fileStorage.containsKey(fileName)) return "";
        int size = fileStorage.remove(fileName);
        String owner = fileOwnership.remove(fileName);

        userUsedStorage.put(owner, userUsedStorage.get(owner) - size);
        userFiles.get(owner).remove(fileName);

        return String.valueOf(size);
    }

    // Level 2: Get N largest files with prefix
    public String getNLargestFilesWithPrefix(String prefix, int n) {
        List<Map.Entry<String, Integer>> filteredFiles = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : fileStorage.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                filteredFiles.add(entry);
            }
        }

        filteredFiles.sort((a, b) -> {
            int sizeCompare = Integer.compare(b.getValue(), a.getValue());
            return sizeCompare != 0 ? sizeCompare : a.getKey().compareTo(b.getKey());
        });

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.min(n, filteredFiles.size()); i++) {
            Map.Entry<String, Integer> entry = filteredFiles.get(i);
            result.append(entry.getKey()).append("(").append(entry.getValue()).append(")");
            if (i < Math.min(n, filteredFiles.size()) - 1) result.append(", ");
        }

        return result.toString();
    }

    // Level 3: User management
    public String addUser(String userId, int capacity) {
        if (userCapacity.containsKey(userId)) return "false";

        userCapacity.put(userId, capacity);
        userUsedStorage.put(userId, 0);
        userFiles.put(userId, new HashSet<>());

        return "true";
    }

    public String addFileByUser(String userId, String fileName, int size) {
        if (fileStorage.containsKey(fileName)) return "";
        if (!userCapacity.containsKey(userId)) return "";

        int used = userUsedStorage.get(userId);
        int limit = userCapacity.get(userId);

        if (used + size > limit) return "";

        fileStorage.put(fileName, size);
        fileOwnership.put(fileName, userId);
        userUsedStorage.put(userId, used + size);
        userFiles.get(userId).add(fileName);

        return String.valueOf(limit - (used + size));
    }

    public String mergeUsers(String targetUserId, String sourceUserId) {
        if (targetUserId.equals(sourceUserId)) return "";
        if (!userCapacity.containsKey(targetUserId) || !userCapacity.containsKey(sourceUserId)) return "";

        // Move all files
        for (String fileName : new HashSet<>(userFiles.get(sourceUserId))) {
            int size = fileStorage.get(fileName);
            fileOwnership.put(fileName, targetUserId);
            userFiles.get(targetUserId).add(fileName);
        }

        // Update capacity and usage
        int updatedUsage = userUsedStorage.get(targetUserId) + userUsedStorage.get(sourceUserId);
        int updatedLimit = userCapacity.get(targetUserId) + userCapacity.get(sourceUserId);

        userUsedStorage.put(targetUserId, updatedUsage);
        userCapacity.put(targetUserId, updatedLimit);

        // Delete source user
        userFiles.remove(sourceUserId);
        userUsedStorage.remove(sourceUserId);
        userCapacity.remove(sourceUserId);

        return String.valueOf(updatedLimit - updatedUsage);
    }

    // Level 4: Backup user files
    public String backupUser(String userId) {
        if (!userCapacity.containsKey(userId)) return "";

        Map<String, Integer> backup = new HashMap<>();
        for (String fileName : userFiles.get(userId)) {
            backup.put(fileName, fileStorage.get(fileName));
        }
        userBackups.put(userId, backup);
        return String.valueOf(backup.size());
    }

    public String restoreUser(String userId) {
        if (!userCapacity.containsKey(userId)) return "";
        Map<String, Integer> backup = userBackups.getOrDefault(userId, new HashMap<>());

        // Remove current files of this user
        for (String fileName : new HashSet<>(userFiles.get(userId))) {
            int size = fileStorage.get(fileName);
            fileStorage.remove(fileName);
            fileOwnership.remove(fileName);
            userUsedStorage.put(userId, userUsedStorage.get(userId) - size);
            userFiles.get(userId).remove(fileName);
        }

        int restoredCount = 0;
        for (Map.Entry<String, Integer> entry : backup.entrySet()) {
            String fileName = entry.getKey();
            int size = entry.getValue();

            // If file name already exists and owned by someone else, skip
            if (fileStorage.containsKey(fileName) && !fileOwnership.get(fileName).equals(userId)) {
                continue;
            }

            // Check if enough capacity
            int used = userUsedStorage.get(userId);
            int limit = userCapacity.get(userId);
            if (used + size > limit) continue;

            fileStorage.put(fileName, size);
            fileOwnership.put(fileName, userId);
            userFiles.get(userId).add(fileName);
            userUsedStorage.put(userId, used + size);
            restoredCount++;
        }

        return String.valueOf(restoredCount);
    }

    // Test everything
    public static void main(String[] args) {
        CloudStorage cloud = new CloudStorage();

        // Users
        System.out.println(cloud.addUser("alice", 1000)); // true
        System.out.println(cloud.addUser("bob", 500));    // true

        // Files
        System.out.println(cloud.addFileByUser("alice", "report.txt", 400)); // 600
        System.out.println(cloud.addFileByUser("alice", "data.csv", 300));   // 300
        System.out.println(cloud.backupUser("alice")); // 2

        System.out.println(cloud.deleteFile("report.txt")); // 400
        System.out.println(cloud.addFileByUser("bob", "report.txt", 200)); // 300

        System.out.println(cloud.restoreUser("alice")); // 1 (report.txt already exists, owned by bob)

        System.out.println(cloud.getNLargestFilesWithPrefix("r", 2)); // report.txt(200)

        System.out.println(cloud.mergeUsers("alice", "bob")); // capacity adjustment
    }
}
