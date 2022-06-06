package me.justacat.projectilemaker.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Hit {


    private List<HitEvent> eventList = new ArrayList<>();

    public Hit(){}
    public Hit(List<HitEvent> eventList) {this.eventList = eventList;}


    public void trigger(Location location, LivingEntity caster) {
        for (HitEvent hitEvent : eventList) {
            hitEvent.trigger(location, caster);
        }
    }


    public List<HitEvent> getEventList() {return eventList;}
    public void setEventList(List<HitEvent> eventList) {this.eventList = eventList;}
    public void modifyEventList(int index, HitEvent event) {eventList.set(index, event);}
    public void addEvent(HitEvent event) {eventList.add(event);}
}
