package ru.job4j.pools;

import org.junit.Test;

import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    private static class User {

        private final String name;

        private final int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return age == user.age
                    && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    @Test
    public void whenArrayIsEmpty() {
        User[] arr = {};
        int result = ParallelSearch.search(arr, new User("User", 1));
        int excepted = -1;
        assertThat(result, is(excepted));
    }

    @Test
    public void whenLinearSearchBecauseArrayStoresLessThan11ItemsAndItemFound() {
        User[] arr = {
                new User("Semyon", 26),
                new User("Alex", 20),
                new User("Konstantin", 45),
                new User("Alic", 27),
                new User("Islam", 24),
                new User("Ira", 19),
                new User("Rina", 21),
                new User("Karina", 24),
                new User("Alina", 22),
                new User("Sergey", 22)
        };
        int result = ParallelSearch.search(arr, new User("Ira", 19));
        int excepted = 5;
        assertThat(result, is(excepted));
    }

    @Test
    public void whenLinearSearchBecauseArrayStoresLessThan11ItemsAndItemNotFound() {
        User[] arr = {
                new User("Semyon", 26),
                new User("Alex", 20),
                new User("Konstantin", 45),
                new User("Alic", 27),
                new User("Islam", 24),
                new User("Ira", 19),
                new User("Rina", 21),
                new User("Karina", 24),
                new User("Alina", 22),
                new User("Sergey", 22)
        };
        int result = ParallelSearch.search(arr, new User("Arina", 29));
        int excepted = -1;
        assertThat(result, is(excepted));
    }

    @Test
    public void whenParallelSearchBecauseArrayStoresMoreThan10ItemsAndItemFound() {
        User[] arr = {
                new User("Semyon", 26),
                new User("Alex", 20),
                new User("Konstantin", 45),
                new User("Alic", 27),
                new User("Islam", 24),
                new User("Ira", 19),
                new User("Rina", 21),
                new User("Karina", 24),
                new User("Alina", 22),
                new User("Sergey", 22),
                new User("Nikolay", 28),
                new User("Luba", 25),
                new User("Boris", 42),
                new User("Petr", 35)
        };
        int result = ParallelSearch.search(arr, new User("Alina", 22));
        int excepted = 8;
        assertThat(result, is(excepted));
    }

    @Test
    public void whenParallelSearchBecauseArrayStoresMoreThan10ItemsAndItemNotFound() {
        User[] arr = {
                new User("Semyon", 26),
                new User("Alex", 20),
                new User("Konstantin", 45),
                new User("Alic", 27),
                new User("Islam", 24),
                new User("Ira", 19),
                new User("Rina", 21),
                new User("Karina", 24),
                new User("Alina", 22),
                new User("Sergey", 22),
                new User("Nikolay", 28),
                new User("Luba", 25),
                new User("Boris", 42),
                new User("Petr", 35)
        };
        int result = ParallelSearch.search(arr, new User("Galina", 42));
        int excepted = -1;
        assertThat(result, is(excepted));
    }

    @Test
    public void whenSearchNullItem() {
        User[] arr = {
                new User("Semyon", 26),
                new User("Alex", 20),
                new User("Konstantin", 45),
                new User("Alic", 27),
                new User("Islam", 24),
                new User("Ira", 19),
                null,
                new User("Karina", 24),
                new User("Alina", 22),
                new User("Sergey", 22),
                new User("Nikolay", 28),
                new User("Luba", 25),
                new User("Boris", 42),
                new User("Petr", 35)
        };
        int result = ParallelSearch.search(arr, null);
        int excepted = 6;
        assertThat(result, is(excepted));
    }
}