package me.justacat.projectilemaker.gui;

import me.justacat.projectilemaker.misc.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class GuiBuilder {

    private HashMap<Integer, ItemStack> slots = new HashMap<>();
    private int size = 36;
    private final Player player;
    private String title = "New GUI";
    private ItemStack empty = new ItemStack(Material.AIR);

    public GuiBuilder(Player player) {
        this.player = player;
    }

    public Inventory toInventory() {

        Inventory inventory = Bukkit.createInventory(player, size, title);
        for (int slot = 0; slot < size; slot++) {

            if (slots.get(slot) != null) {
                inventory.setItem(slot, slots.get(slot));
            } else {
                inventory.setItem(slot, empty);
            }

        }


        return inventory;
    }


    public void setItem(int slot, Material itemType, int amount, String name, List<String> lore, boolean hideFlags, String localizedName) {

        ItemStack itemStack = prepareItem(itemType, amount, name, lore, hideFlags, localizedName);
        Integer hashSlot = slot;
        slots.put(hashSlot, itemStack);

    }

    public void setItem(int slot, Material itemType, int amount, String name, List<String> lore, boolean hideFlags) {

        ItemStack itemStack = prepareItem(itemType, amount, name, lore, hideFlags, null);
        Integer hashSlot = slot;
        slots.put(hashSlot, itemStack);

    }

    public void setItem(int slot, ItemStack itemStack) {

        Integer hashSlot = slot;
        slots.put(hashSlot, itemStack);


    }

    private ItemStack prepareItem(Material itemType, int amount, String name, List<String> lore, boolean hideFlags, String localizedName) {
        ItemStack itemStack = new ItemStack(itemType, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Chat.colorMessage(name));
        int i = 0;
        if (lore != null) {
            for (String line : lore) {
                lore.set(i, Chat.colorMessage(line));
                i++;
            }
            itemMeta.setLore(lore);
        }

        if (localizedName != null) {
            itemMeta.setLocalizedName(localizedName);
        }

        if (hideFlags) {
            for (ItemFlag itemFlag : ItemFlag.values()) {
                itemMeta.addItemFlags(itemFlag);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void loadHashMap(HashMap<Integer, ItemStack> map) {
        slots = map;
    }

    public void setEmpty(Material itemType, int amount, String name, List<String> lore, boolean hideFlags, String localizedName) {
        this.empty = prepareItem(itemType, amount, name, lore, hideFlags, localizedName);
    }

    public void setEmpty(Material itemType, int amount, String name, List<String> lore, boolean hideFlags) {
        this.empty = prepareItem(itemType, amount, name, lore, hideFlags, null);
    }

    public void setTitle(String title) {
        this.title = Chat.colorMessage(title);
    }

    public void setSize(int size) {
        this.size = size;
    }
}
