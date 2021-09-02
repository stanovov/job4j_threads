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
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = storage.get(fromId);
        User to = storage.get(toId);
        boolean result = from != null && to != null && from.getAmount() >= amount;
        if (!result) {
            System.out.println("Operation is not possible!");
        } else {
            update(new User(fromId, from.getAmount() - amount));
            update(new User(toId, to.getAmount() + amount));
        }
        return result;
    }
}
