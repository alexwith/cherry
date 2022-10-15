package net.cherry.test;

import net.cherry.entity.Entity;

public class CherryTest {

    public static void main(String[] args) {
        final TestEntity test = new TestEntity();

        final TestEntity foundTest = Entity.find();
    }
}
