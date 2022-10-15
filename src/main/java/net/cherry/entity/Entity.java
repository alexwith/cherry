package net.cherry.entity;

public interface Entity {

    static <T extends Entity> T find() {
        return null;
    }
}
