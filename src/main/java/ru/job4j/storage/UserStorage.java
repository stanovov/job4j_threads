package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final List<User> userList = new ArrayList<>();

    public synchronized User findById(int id) {
        int index = indexOf(id);
        return (index == -1) ? null : userList.get(index);
    }

    public synchronized boolean add(User user) {
        return userList.add(user);
    }

    public synchronized boolean update(User user) {
        int id = indexOf(user.getId());
        boolean result = id != -1;
        if (result) {
            userList.set(id, user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return userList.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        int indexFromId = indexOf(fromId);
        int indexToId = indexOf(toId);
        if (indexFromId == -1 || indexToId == -1) {
            if (indexFromId == -1 && indexToId == -1) {
                System.out.println("No accounts found!");
            } else {
                System.out.println("Account with id \"" + ((indexFromId == -1) ? fromId : toId) + "\" not found");
            }
        } else {
            User fromUser = userList.get(indexFromId);
            User toUser = userList.get(indexToId);
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

    private synchronized int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < userList.size(); index++) {
            if (userList.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}
