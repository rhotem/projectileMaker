package me.justacat.projectilemaker.commands;

import me.justacat.projectilemaker.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {


        if (args.length == 1) {
            List<String> list = new ArrayList<>();

            list.add("Menu");
            list.add("Help");
            list.add("BindItem");


            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());


        } else if (args.length == 2) {


            List<String> projectiles = FileManager.getProjectileList();

            return StringUtil.copyPartialMatches(args[1], projectiles, new ArrayList<>());



        }

        return null;



    }
}
