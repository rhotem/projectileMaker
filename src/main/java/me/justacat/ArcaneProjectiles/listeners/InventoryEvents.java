package me.justacat.ArcaneProjectiles.listeners;

import me.justacat.ArcaneProjectiles.FileManager;
import me.justacat.ArcaneProjectiles.gui.GuiBuilder;
import me.justacat.ArcaneProjectiles.gui.ProjectileMenu;
import me.justacat.ArcaneProjectiles.misc.Chat;
import me.justacat.ArcaneProjectiles.misc.NBT;
import me.justacat.ArcaneProjectiles.misc.Parameter;
import me.justacat.ArcaneProjectiles.projectiles.Projectile;
import me.justacat.ArcaneProjectiles.projectiles.hitevents.HitEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryEvents implements Listener {


    public static HashMap<UUID, String> clickTypeEdit = new HashMap<>();

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        if (e.getRawSlot() >= e.getInventory().getSize()) {return;}

        if (e.getInventory().getItem(e.getRawSlot()) == null) {return;}

        ItemStack item = e.getInventory().getItem(e.getRawSlot());

        String title = e.getView().getTitle();

        if (title.contains("Projectile Maker")) {

            e.setCancelled(true);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {

                String localName = item.getItemMeta().getLocalizedName();
                if (localName.equals("CreateProjectile"))  {
                    Chat.sendPlayerChatRequest(player, "newProjectile");
                } else if (localName.equalsIgnoreCase("projectile")) {
                    ProjectileMenu.editProjectile(item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), ""), player);
                }


           }

        } else if (title.contains("Edit Projectile: ")) {
            e.setCancelled(true);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLocalizedName()) {

                if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                    ProjectileMenu.openProjectileMenu(player);

                } else if (item.getItemMeta().getLocalizedName().equals("hitEffects")) {

                    ProjectileMenu.editHitEffects(player);


                } else {

                    Projectile projectile = Projectile.projectileFromName(ProjectileMenu.projectileEditMap.get(player.getUniqueId()), true);
                    String setting = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");
                    if (setting.equals("Type")) {
                        if (projectile.getType().equals("Beam")) {
                            projectile.getParameterByName("Type").chatEdit("Spiral");
                        } else if (projectile.getType().equals("Spiral")) {
                            projectile.getParameterByName("Type").chatEdit("Physical");
                        } else {
                            projectile.getParameterByName("Type").chatEdit("Beam");
                        }
                        player.playSound(player, Sound.BLOCK_LEVER_CLICK, 0.5F, 0.5F);
                        ProjectileMenu.editProjectile(ProjectileMenu.projectileEditMap.get(player.getUniqueId()), player);
                    } else {
                        Chat.sendPlayerChatRequest(player, "EDIT:" + setting);
                    }

                }




            }



        } else if (title.contains("Edit Hit Events: ")) {

            e.setCancelled(true);


            if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                ProjectileMenu.editProjectile(ProjectileMenu.projectileEditMap.get(player.getUniqueId()), player);
            } else if (item.getItemMeta().getLocalizedName().equals("newHitEvent")) {


                ProjectileMenu.createHitEvent(player);



            } else {
                try {
                    ProjectileMenu.editHitEffect(player, Integer.parseInt(item.getItemMeta().getLocalizedName()));
                } catch (NumberFormatException ignored) {

                }


            }


        } else if (title.contains("Edit hit event: Event ")) {

            e.setCancelled(true);

            if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                ProjectileMenu.editHitEffects(player);
            } else if (item.getType().equals(Material.GREEN_DYE)) {

                if (item.getItemMeta().getLocalizedName().toUpperCase().contains("BOOLEAN")) {

                    String projectileName = ProjectileMenu.projectileEditMap.get(player.getUniqueId());
                    Projectile projectile = Projectile.loadedProjectiles.get(projectileName);

                    int hitIndex = ProjectileMenu.hitEventEditMap.get(player.getUniqueId()) - 1;
                    HitEvent hitEvent = projectile.getHitEventList().get(hitIndex);

                    String settingName = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");
                    Parameter<?> parameter = hitEvent.getParameterByName(settingName);

                    if ((Boolean) parameter.getValue()) {
                        ((Parameter<Boolean>) parameter).setValue(Boolean.FALSE);
                    } else {
                        ((Parameter<Boolean>) parameter).setValue(Boolean.TRUE);
                    }
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK, 0.3F, 0.3F);
                    ProjectileMenu.editHitEffect(player, hitIndex + 1);
                    projectile.saveProjectile();
                } else {
                    Chat.sendPlayerChatRequest(player, "EditHitEvent:" + item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), ""));
                }


            } else if (item.getItemMeta().getLocalizedName().equals("delete")) {

                String projectileName = ProjectileMenu.projectileEditMap.get(player.getUniqueId());
                Projectile projectile = Projectile.loadedProjectiles.get(projectileName);

                int hitIndex = ProjectileMenu.hitEventEditMap.get(player.getUniqueId()) - 1;

                projectile.deleteHitEvent(hitIndex);
                projectile.saveProjectile();
                ProjectileMenu.editHitEffects(player);

            }


        } else if (title.equals("New Hit Event")) {

            e.setCancelled(true);

            if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                ProjectileMenu.editHitEffects(player);
            } else {

                Projectile projectile = Projectile.projectileFromName(ProjectileMenu.projectileEditMap.get(player.getUniqueId()), true);

                String name = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");


                if (HitEvent.hitEvents.containsKey(name)) {
                    HitEvent hitEvent = HitEvent.hitEvents.get(name);
                    projectile.addHitEvent(hitEvent);
                    projectile.saveProjectile();
                    ProjectileMenu.editHitEffects(player);
                }



            }

        } else if (title.equals("Edit Item's Projectiles")) {

            e.setCancelled(true);
            if (item.getType().equals(Material.ORANGE_STAINED_GLASS_PANE)) {
                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                    ProjectileMenu.openItemMenu(player, e.getCursor());
                }

            } else if (e.getRawSlot() == 22) {

                player.getInventory().addItem(item);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.2F, 0.5F);
                ProjectileMenu.openEmptyItemMenu(player);

            } else if (item.getType().equals(Material.BLAZE_POWDER)) {

                int[] right = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44};
                int[] left = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 28 ,39};

                String type = null;
                for (int slot : right) {
                    if (slot == e.getRawSlot()) {
                        type = "rightClick";
                        break;
                    }
                }

                for (int slot : left) {

                    if (slot == e.getRawSlot()) {
                        type = "leftClick";
                        break;
                    }

                }

                ItemStack editItem = e.getInventory().getItem(22);

                if (type == null) return;

                ProjectileMenu.openItemMenu(player, NBT.removeProjectile(editItem, Integer.parseInt(item.getItemMeta().getLocalizedName()), type));

            } else if (item.getType().equals(Material.BOOK)) {

                if (item.getItemMeta().getDisplayName().contains("Left")) {
                    clickTypeEdit.put(player.getUniqueId(), "leftClick");
                } else {
                    clickTypeEdit.put(player.getUniqueId(), "rightClick");
                }

                chooseProjectile(player);
            }


        } else if (title.equals("Arcane Projectiles")) {

            e.setCancelled(true);

            if (item.getItemMeta().getDisplayName().equals(Chat.colorMessage("&bProjectile Maker"))) {
                ProjectileMenu.openProjectileMenu(player);
            } else if (item.getItemMeta().getDisplayName().equals(Chat.colorMessage("&bItem Binder"))) {
                ProjectileMenu.openLastItemMenu(player);
            }


        } else if (title.equals("Choose Projectile")) {

            e.setCancelled(true);

            String projectile = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");

            ProjectileMenu.openLastItemMenu(player);

            ItemStack editItem = player.getOpenInventory().getItem(22);

            if (clickTypeEdit.get(player.getUniqueId()) == null) return;

            ProjectileMenu.openItemMenu(player, NBT.addProjectile(editItem, clickTypeEdit.get(player.getUniqueId()), projectile));

        }


    }


    private void chooseProjectile(Player player) {

        List<String> projectiles = FileManager.getProjectileList();

        GuiBuilder guiBuilder = new GuiBuilder(player);

        guiBuilder.setTitle("Choose Projectile");


        if (projectiles.size() > 30) {
            guiBuilder.setSize(54);
        } else {
            guiBuilder.setSize(36);
        }

        int slot = 0;

        for (String projectile : projectiles) {

            guiBuilder.setItem(slot, Material.BLAZE_POWDER, 1, "&7" + projectile, null, true);
            slot++;
        }
        player.openInventory(guiBuilder.toInventory());



    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        if (e.getView().getTitle().equals("Edit Item's Projectiles")) {

            ProjectileMenu.editedItem.put(e.getPlayer().getUniqueId(), e.getInventory().getItem(22));

        }

    }
}
