package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAddToCacheThenGet() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base result = cache.get(1);
        Base expected = new Base(1, 0);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddToCacheAndSuccessfullyUpdateThenGet() {
        Base original = new Base(1, 0);
        Base updated = new Base(1, 0);
        updated.setName("Test");
        Cache cache = new Cache();
        cache.add(original);
        cache.update(updated);
        Base result = cache.get(1);
        Base expected = new Base(1, 1);
        expected.setName("Test");
        assertThat(result, is(expected));
    }

    @Test(expected = OptimisticException.class)
    public void whenAddToCacheAndUnsuccessfullyUpdateThenGet() {
        Base original = new Base(1, 0);
        Base updated = new Base(1, 1);
        updated.setName("Test");
        Cache cache = new Cache();
        cache.add(original);
        cache.update(updated);
    }

    @Test
    public void whenAddToCacheAndDeleteThenGet() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(new Base(1, 0));
        assertNull(cache.get(base.getId()));
    }
}