package me.justacat.projectilemaker.commands;

import me.justacat.projectilemaker.gui.ProjectileMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ProjectileMakerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            ProjectileMenu.openProjectileMenu((Player) sender);
        }
        return false;
    }
}
