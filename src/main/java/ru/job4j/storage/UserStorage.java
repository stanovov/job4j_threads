package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized User findById(int id) {
        return storage.get(id);
    }

    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return storage.computeIfPresent(user.getId(), (k, v) -> user) != null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = storage.get(fromId);
        User to = storage.get(toId);
        if (from == null && to == null) {
            System.out.println("No accounts found!");
        } else if (from == null || to == null) {
            System.out.println("Account with id \"" + (from == null ? fromId : toId) + "\" not found");
        } else if (from.getAmount() < amount) {
            System.out.println("Insufficient funds for transfer");
        } else {
            update(new User(fromId, from.getAmount() - amount));
            update(new User(toId, to.getAmount() + amount));
            result = true;
        }
        return result;
    }
}
