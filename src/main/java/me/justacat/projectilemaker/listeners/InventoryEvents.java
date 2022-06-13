package me.justacat.projectilemaker.listeners;

import me.justacat.projectilemaker.gui.ProjectileMenu;
import me.justacat.projectilemaker.misc.Chat;
import me.justacat.projectilemaker.misc.Parameter;
import me.justacat.projectilemaker.projectiles.HitEventStorage;
import me.justacat.projectilemaker.projectiles.Projectile;
import me.justacat.projectilemaker.projectiles.hitevents.HitEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {

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
                    String projectileName = e.getView().getTitle().replace("Edit Projectile: ", "");
                    String settingType = item.getItemMeta().getLocalizedName();
                    String setting = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");
                    Chat.sendPlayerChatRequest(player, "EDIT:" + setting);
                }




            }



        } else if (title.contains("Edit Hit Events: ")) {

            e.setCancelled(true);


            String projectileName = e.getView().getTitle().replace("Edit Hit Events: ", "");
            Projectile projectile = Projectile.projectileFromName(projectileName, true);

            if (item.getItemMeta().getLocalizedName().equals("goBack")) {
                ProjectileMenu.editProjectile(ProjectileMenu.projectileEditMap.get(player.getUniqueId()), player);
            } else if (item.getItemMeta().getLocalizedName().equals("newHitEvent")) {

                projectile.addHitEvent(new HitEventStorage(5, false, true));
                projectile.saveProjectile();
                ProjectileMenu.editHitEffects(player);

            } else {

                ProjectileMenu.editHitEffect(player, Integer.parseInt(item.getItemMeta().getLocalizedName()));

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
                    HitEvent hitEvent = projectile.getHitEventStorageList().get(hitIndex).getHitEvent();

                    String settingName = item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), "");
                    Parameter<?> parameter = hitEvent.getParameterByName(settingName);

                    if ((Boolean) parameter.getValue()) {
                        ((Parameter<Boolean>) parameter).setValue(Boolean.FALSE);
                    } else {
                        ((Parameter<Boolean>) parameter).setValue(Boolean.TRUE);
                    }
                    ProjectileMenu.editHitEffect(player, hitIndex + 1);
                    projectile.saveProjectile();
                } else {
                    Chat.sendPlayerChatRequest(player, "EditHitEvent:" + item.getItemMeta().getDisplayName().replace(ChatColor.GRAY.toString(), ""));
                }


            }


        }


    }


}
