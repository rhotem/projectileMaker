package me.justacat.ArcaneProjectiles.commands;

import me.justacat.ArcaneProjectiles.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {


        if (args.length == 1) {
            List<String> list = new ArrayList<>();

            list.add("Menu");
            list.add("Help");
            list.add("BindItem");
            list.add("Reload");
            list.add("Repair");


            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());


        } else if (args.length == 2) {

            if (args[0].equals("BindItem")) {
                List<String> projectiles = FileManager.getProjectileList();

                return StringUtil.copyPartialMatches(args[1], projectiles, new ArrayList<>());
            }





        } else if (args.length == 3) {

            if (args[0].equals("BindItem")) {
                return StringUtil.copyPartialMatches(args[2], Arrays.asList("RightClick", "LeftClick"), new ArrayList<>());
            }
        }

        return null;



    }
}
