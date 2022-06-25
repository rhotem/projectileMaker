package me.justacat.ArcaneProjectiles.gui;

import com.jeff_media.morepersistentdatatypes.DataType;
import me.justacat.ArcaneProjectiles.ArcaneProjectiles;
import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.misc.Chat;
import me.justacat.ArcaneProjectiles.misc.Parameter;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.HitEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ProjectileMenu {


    public static HashMap<UUID, String> projectileEditMap = new HashMap<>();
    public static HashMap<UUID, Integer> hitEventEditMap = new HashMap<>();

    public static HashMap<UUID, ItemStack> editedItem = new HashMap<>();

    public static void openMainMenu(Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setTitle("Arcane Projectiles");

        guiBuilder.setSize(27);
        guiBuilder.setItem(11, Material.BLAZE_ROD, 1, "&bProjectile Maker", Arrays.asList("&0", "&7Click here to edit & create projectiles!", "&0"), true);
        guiBuilder.setItem(15, Material.IRON_SWORD, 1, "&bItem Binder", Arrays.asList("&0", "&7Click here to add & remove projectiles from an item!", "&0"), true);

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, "&0", null, true);


        player.openInventory(guiBuilder.toInventory());
    }

    public static void openProjectileMenu(Player player) {

        List<String> list = FileManager.getProjectileList();


        GuiBuilder builder = new GuiBuilder(player);
        //PagesGUI pagesGUI = new PagesGUI(45, projectiles);

        builder.setSize(54);
        builder.setTitle("&0Projectile Maker");

        int slot = 0;
        for (String string : list) {
            Material material = (Material) Projectile.projectileFromName(string, true).getParameterByName("Display").getValue();
            builder.setItem(slot, material, 1, Chat.colorMessage("&7" + string), null, true, "Projectile");
            slot++;
        }



        builder.setItem(
                slot,
                Material.ITEM_FRAME,
                1,
                "&7New Projectile",
                Arrays.asList("&0" ,"&7Click here to create new projectile!"),
                true,
                "CreateProjectile"
                );

        builder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);

        player.openInventory(builder.toInventory());


    }

    public static void editProjectile(String name, Player player) {


        projectileEditMap.put(player.getUniqueId(), name);
        GuiBuilder guiBuilder = new GuiBuilder(player);
        guiBuilder.setSize(54);

        guiBuilder.setTitle("Edit Projectile: " + name);
        List<String> lore = new ArrayList<>();
        lore.add("   ");
        lore.add(Chat.colorMessage("&eClick here to edit this value!"));

        Projectile projectile = Projectile.projectileFromName(name, true);

        if (projectile == null) {
            System.out.println("error: this projectile seems to be null!");
            return;
        }

        int slot = 0;

        for (Parameter<?> parameter : projectile.getParameters()) {

            if (lore.size() > 2) {
                lore.remove(2);
            }

            lore.add("&eValue: &f" + parameter.getValue());
            guiBuilder.setItem(slot, parameter.getDisplay(), 1, "&7" + parameter.getName(), lore, true, "Editable");

            slot++;

        }

        if (lore.size() > 2) {
            lore.remove(2);
        }

        guiBuilder.setItem(slot, Material.TNT_MINECART, 1, Chat.colorMessage("&7Hit Effects"), lore, true, "hitEffects");




        guiBuilder.setItem(49, Material.ARROW, 1, "&fGo Back", null, true, "goBack");

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);
        player.openInventory(guiBuilder.toInventory());
    }


    public static void editHitEffects(Player player) {

        String projectileName = projectileEditMap.get(player.getUniqueId());
        Projectile projectile = Projectile.projectileFromName(projectileName, true);

        List<HitEvent> hitEffects = projectile.getHitEventList();

        GuiBuilder guiBuilder = new GuiBuilder(player);
        guiBuilder.setSize(54);

        guiBuilder.setTitle("Edit Hit Events: " + projectileName);


        int slot = 0;
        for (HitEvent hit : hitEffects) {

            guiBuilder.setItem(slot, HitEvent.nameToMaterial.get(hit.getName()), 1, "&7Event " + (slot + 1) + ": " + hit.getName(), Arrays.asList("&0", "&aClick here to edit!"), true, String.valueOf(slot + 1));


            slot++;

        }

        guiBuilder.setItem(slot, Material.WRITABLE_BOOK, 1, "&7Add Hit Event", Arrays.asList("&0", "&7Click here to add hit event!"), true, "newHitEvent");


        guiBuilder.setItem(49, Material.ARROW, 1, "&fGo Back", null, true, "goBack");

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);


        player.openInventory(guiBuilder.toInventory());


    }

    public static void editHitEffect(Player player, int number) {

        hitEventEditMap.put(player.getUniqueId(), number);
        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setSize(36);
        guiBuilder.setTitle("Edit hit event: Event " + number);

        String projectileName = projectileEditMap.get(player.getUniqueId());
        Projectile projectile = Projectile.projectileFromName(projectileName, true);
        HitEvent hitEvent = projectile.getHitEventList().get(number - 1);

        int slot = 0;

        List<String> lore = new ArrayList<>();
        lore.add("   ");
        lore.add(Chat.colorMessage("&eClick here to edit this value"));
        lore.add("   ");


        for (Parameter<?> parameter : hitEvent.getParameters()) {
            if (lore.size() > 3) {
                lore.remove(3);
            }

            lore.add("&7Value: " + parameter.getValue());
            guiBuilder.setItem(slot, Material.GREEN_DYE, 1, "&7" + parameter.getName(), lore, true, parameter.getValue().getClass().toString());

            slot++;
        }

        guiBuilder.setItem(31, Material.ARROW, 1, "&fGo Back", null, true, "goBack");

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);

        guiBuilder.setItem(35, Material.REDSTONE_BLOCK, 1, "&cDelete Hit Event", null, true, "delete");


        player.openInventory(guiBuilder.toInventory());
    }


    public static void createHitEvent(Player player) {

        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setTitle("New Hit Event");

        guiBuilder.setSize(36);

        guiBuilder.setItem(31, Material.ARROW, 1, "&fGo Back", null, true, "goBack");

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, " ", null, true);


        int slot = 0;

        for (HitEvent hitEvent : HitEvent.getHitEvents()) {
            guiBuilder.setItem(slot, HitEvent.nameToMaterial.get(hitEvent.getName()), 1, "&7" + hitEvent.getName(), Arrays.asList("&0", "&b" + HitEvent.nameToDescription.get(hitEvent.getName())), true);
            slot++;
        }

        player.openInventory(guiBuilder.toInventory());
    }

    public static void openEmptyItemMenu(Player player) {

        editedItem.remove(player.getUniqueId());

        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setEmpty(Material.GRAY_STAINED_GLASS_PANE, 1, "&0", null, true);

        guiBuilder.setTitle("Edit Item's Projectiles");

        guiBuilder.setSize(27);

        guiBuilder.setItem(13, Material.ORANGE_STAINED_GLASS_PANE, 1, "&cInsert Item Here!", null, true);

        player.openInventory(guiBuilder.toInventory());
    }

    public static void openItemMenu(Player player, ItemStack item) {

        editedItem.put(player.getUniqueId(), item);

        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setSize(45);

        guiBuilder.setItem(22, item);

        guiBuilder.setTitle("Edit Item's Projectiles");

        for (int slot : new int[]{4, 13, 31, 40}) {
            guiBuilder.setItem(slot, Material.GRAY_STAINED_GLASS_PANE, 1, "&0", null, true);
        }

        int[] right = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44};
        int[] left = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 28 ,39};

        ItemMeta itemMeta = item.getItemMeta();

        NamespacedKey leftClick = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), "LeftClick");
        NamespacedKey rightClick = new NamespacedKey(JavaPlugin.getPlugin(ArcaneProjectiles.class), "RightClick");

        List<String> rightProjectiles = itemMeta.getPersistentDataContainer().get(rightClick, DataType.asList(DataType.STRING));
        List<String> leftProjectiles = itemMeta.getPersistentDataContainer().get(leftClick, DataType.asList(DataType.STRING));

        if (rightProjectiles == null) rightProjectiles = new ArrayList<>();
        if (leftProjectiles == null) leftProjectiles = new ArrayList<>();

        int leftSlot = 0;
        int rightSlot = 0;

        for (String projectile : leftProjectiles) {

            guiBuilder.setItem(left[leftSlot], Material.BLAZE_POWDER, 1, "&a" + projectile, Arrays.asList("&0", "&4Click Here To Remove!", "&0"), true, String.valueOf(leftSlot));
            leftSlot++;

        }

        for (String projectile : rightProjectiles) {

            guiBuilder.setItem(right[rightSlot], Material.BLAZE_POWDER, 1, "&a" + projectile, Arrays.asList("&0", "&4Click Here To Remove!", "&0"), true, String.valueOf(rightSlot));
            rightSlot++;

        }

        guiBuilder.setItem(right[rightSlot], Material.BOOK, 1, "&7Add &bRight-click&7 Projectile!", Arrays.asList("&0", "&aClick here to add a projectile!", "&0"), true);
        guiBuilder.setItem(left[leftSlot], Material.BOOK, 1, "&7Add &bLeft-click&7 Projectile!", Arrays.asList("&0", "&aClick here to add a projectile!", "&0"), true);

        player.openInventory(guiBuilder.toInventory());
    }

    public static void openLastItemMenu(Player player) {

        if (editedItem.containsKey(player.getUniqueId())) {
            openItemMenu(player, editedItem.get(player.getUniqueId()));
        } else {
            openEmptyItemMenu(player);
        }

    }
}
