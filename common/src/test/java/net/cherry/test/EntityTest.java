package net.cherry.test;

import java.util.List;
import java.util.Map;
import net.cherry.Cherry;
import net.cherry.test.entity.TestEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityTest {

    @Test
    public void creationTest() {
        final TestEntity entity = Cherry.create(TestEntity.class);

        Assertions.assertEquals("Bob", entity.getName());
        Assertions.assertEquals(18, entity.getAge());
        Assertions.assertEquals(List.of(
            "Do the dishes",
            "Make dinner"
        ), entity.getTodo());
        Assertions.assertEquals(Map.of(
            "Stones", 4
        ), entity.getItems());
    }
}
