package me.justacat.projectilemaker.projectiles;

import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;

public class HitEventStorage<T extends HitEvent> {

    private T hitEvent;

    public HitEventStorage(T hitEvent) {
        this.hitEvent = hitEvent;
    }

    public T getHitEvent() {
        return hitEvent;
    }
}
