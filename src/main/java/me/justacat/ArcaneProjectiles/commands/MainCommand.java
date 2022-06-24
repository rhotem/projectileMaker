package me.justacat.ArcaneProjectiles.commands;

import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.gui.ProjectileMenu;
import me.justacat.ArcaneProjectiles.misc.Chat;
import me.justacat.ArcaneProjectiles.misc.NBT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand implements CommandExecutor {

    private final String helpMessage = Chat.colorMessage("&e/ArcaneProjectiles Menu \n&e/ArcaneProjectiles BindItem \n&e/ArcaneProjectiles Help \n&e/ArcaneProjectiles Reload");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {

            if (args.length >= 1) {

                switch (args[0].toUpperCase()) {

                    case "MENU":
                        ProjectileMenu.openMainMenu((Player) sender);
                        break;
                    case "BINDITEM":


                        if (args.length == 3) {

                            List<String> projectileList = FileManager.getProjectileList();
                            if (projectileList.contains(args[1].replace(" ", ""))) {
                                ((Player) sender).getInventory().setItemInMainHand(NBT.addProjectile(((Player) sender).getInventory().getItemInMainHand(), args[2], args[1]));
                            }


                        } else {
                            sender.sendMessage("Please specify a projectile name and a click type!");
                        }
                        break;
                    case "RELOAD":
                        boolean success = ArcaneProjectiles.reload();
                        if (success) {
                            sender.sendMessage("Reloaded!");
                        } else {
                            sender.sendMessage("There was an error while reloading the plugin. Look at the console for more details.");
                        }
                        break;
                    default:
                        sender.sendMessage(helpMessage);
                        break;
                }

            } else {
                sender.sendMessage(helpMessage);
            }



        }
        return false;
    }
}
