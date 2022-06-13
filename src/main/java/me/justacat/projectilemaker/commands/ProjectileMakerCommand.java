package me.justacat.projectilemaker.commands;

import me.justacat.projectilemaker.FileManager;
import me.justacat.projectilemaker.ProjectileMaker;
import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import me.justacat.projectilemaker.misc.NBT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectileMakerCommand implements CommandExecutor {

    private final String helpMessage = Chat.colorMessage("&e/ProjectileMaker Menu \n&e/ProjectileMaker BindItem \n&e/ProjectileMaker Help \n&e/ProjectileMaker Reload");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {

            if (args.length >= 1) {

                switch (args[0].toUpperCase()) {

                    case "MENU":
                        ProjectileMenu.openProjectileMenu((Player) sender);
                        break;
                    case "BINDITEM":


                        if (args.length == 3) {

                            List<String> projectileList = FileManager.getProjectileList();
                            if (projectileList.contains(args[1].replace(" ", ""))) {
                                ((Player) sender).getInventory().setItemInMainHand(NBT.putProjectile(((Player) sender).getInventory().getItemInMainHand(), args[2], args[1]));
                            }


                        } else {
                            sender.sendMessage("Please specify a projectile name and a click type!");
                        }
                        break;
                    case "RELOAD":
                        boolean success = ProjectileMaker.reload();
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
