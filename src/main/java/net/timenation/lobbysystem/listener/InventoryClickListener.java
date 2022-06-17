package net.timenation.lobbysystem.listener;

import eu.thesimplecloud.api.CloudAPI;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null)
            return;

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.navigator"))) {
            switch (event.getSlot()) {
                case 40 -> {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 2, 1);
                    player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.5, 0)));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
                    Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), () -> {
                        player.sendTitle(TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:2ea38c>Spawn</GRADIENT:2ea34f>"), I18n.format(player, "lobby.title.wasteleported"), 10, 15, 10);
                        player.teleport(new Location(Bukkit.getWorld("world"), 56.5, 20, 5.5, 90, 0));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                    }, 20);
                }
                case 24 -> {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 2, 1);
                    player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.5, 0)));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
                    Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), () -> {
                        player.sendTitle(TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:3653bf>1vs1</GRADIENT:566ec4>"), I18n.format(player, "lobby.title.wasteleported"), 10, 15, 10);
                        player.teleport(new Location(Bukkit.getWorld("world"), 54.5, 26.5, 73.5, -99, 0));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                    }, 20);
                }
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npc1vsbot"))) {
            if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()).connect(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getCurrentItem().getItemMeta().getDisplayName().replace("§x§2§2§6§7§D§6» ", "")));
            }
        }
    }
}
