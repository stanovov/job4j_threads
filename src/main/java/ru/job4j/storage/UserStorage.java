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
        return storage.put(user.getId(), user) != null;
    }

    public synchronized boolean update(User user) {
        int id = user.getId();
        boolean result = storage.containsKey(id);
        if (result) {
            storage.put(user.getId(), user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        boolean fromFound = storage.containsKey(fromId);
        boolean toFound = storage.containsKey(toId);
        if (!fromFound || !toFound) {
            if (!fromFound && !toFound) {
                System.out.println("No accounts found!");
            } else {
                System.out.println("Account with id \"" + (!fromFound ? fromId : toId) + "\" not found");
            }
        } else {
            User fromUser = storage.get(fromId);
            User toUser = storage.get(toId);
            result = fromUser.getAmount() >= amount;
            if (!result) {
                System.out.println("Insufficient funds for transfer");
            } else {
                update(new User(fromId, fromUser.getAmount() - amount));
                update(new User(toId, toUser.getAmount() + amount));
            }
        }
        return result;
    }
}
