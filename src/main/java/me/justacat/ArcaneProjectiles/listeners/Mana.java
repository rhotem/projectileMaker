package me.justacat.ArcaneProjectiles.listeners;

import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.misc.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Mana implements Listener {


    public static HashMap<UUID, Double> manaMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        FileConfiguration config = ArcaneProjectiles.instance.getConfig();



        if (!config.getBoolean("Mana.Enabled")) return;

        manaMap.put(player.getUniqueId(), config.getDouble("Mana.MaxMana"));

        new BukkitRunnable() {
            @Override
            public void run() {


                if (config.getBoolean("Mana.Enabled")) {

                    double mana = manaMap.get(player.getUniqueId());

                    mana = mana + config.getDouble("Mana.ManaRegen") / 5;

                    if (mana > config.getDouble("Mana.MaxMana")) {mana = config.getDouble("Mana.MaxMana");}

                    manaMap.put(player.getUniqueId(), mana);

                    if (config.getBoolean("Mana.ActionBar")) {

                        player.sendActionBar(Chat.colorMessage(config.getString("Mana.Display").replace("{ManaLeft}", String.valueOf(mana)).replace("{MaxMana}", String.valueOf(config.getDouble("Mana.MaxMana")))));

                    }

                }


            }
        }.runTaskTimer(ArcaneProjectiles.instance, 0, 4);



    }

    public static void setMana(Player player, double value) {manaMap.put(player.getUniqueId(), value);}

    public static double getMana(Player player) {return manaMap.get(player.getUniqueId());}

    public static void addMana(Player player, double amount) {setMana(player, getMana(player) + amount);}
}
