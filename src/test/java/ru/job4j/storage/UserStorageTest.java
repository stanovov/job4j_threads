package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void whenExecute2ThreadAndTransferBetween2Users() throws InterruptedException {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        Thread first = new Thread(() -> storage.transfer(1, 2, 50));
        Thread second = new Thread(() -> storage.transfer(2, 1, 80));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.findById(1).getAmount(), is(130));
        assertThat(storage.findById(2).getAmount(), is(170));
    }

    @Test
    public void whenExecute3ThreadAndTransferBetween3Users() throws InterruptedException {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 25));
        storage.add(new User(2, 25));
        storage.add(new User(3, 25));
        Thread first = new Thread(() -> storage.transfer(1, 2, 25));
        Thread second = new Thread(() -> storage.transfer(2, 3, 25));
        Thread third = new Thread(() -> storage.transfer(3, 1, 25));
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(storage.findById(1).getAmount(), is(25));
        assertThat(storage.findById(2).getAmount(), is(25));
        assertThat(storage.findById(3).getAmount(), is(25));
    }
}