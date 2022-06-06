package me.justacat.projectilemaker.commands;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.projectiles.Projectile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class testCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Projectile projectile = new Projectile("test");

        projectile.setType("Beam");
        projectile.setDelay(1);
        projectile.setRange(30);
        projectile.setVelocity(10);

        FileManager.createJSON("TestProjectile", FileManager.projectilesFolder, projectile, true);

        return false;
    }
}
