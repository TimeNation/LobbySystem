package net.timenation.lobbysystem.listener;

import eu.thesimplecloud.api.CloudAPI;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
            switch (event.getCurrentItem().getType()) {
                case GLISTERING_MELON_SLICE -> {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 2, 1);
                    player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.5, 0)));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
                    Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), () -> {
                        player.sendTitle(TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:2ea38c>Spawn</GRADIENT:2ea34f>"), I18n.format(player, "lobby.title.wasteleported"), 10, 15, 10);
                        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 101, 1.5));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                    }, 20);
                }
                case FIREWORK_ROCKET ->
                        teleportPlayer(player, new Location(Bukkit.getWorld("world"), -34.5, 99, -10.5, 68, 0), "lobby.inventory.navigator.item.stagebattle");
                case NETHERITE_AXE ->
                        teleportPlayer(player, new Location(Bukkit.getWorld("world"), 14.5, 100, -23.5, -142, 0), "lobby.inventory.navigator.item.magicpvp");
                case LIME_BED ->
                        teleportPlayer(player, new Location(Bukkit.getWorld("world"), -25.5, 98, -24.5, 159, 0), "lobby.inventory.navigator.item.bedwars");
                case STONE_SWORD ->
                        teleportPlayer(player, new Location(Bukkit.getWorld("world"), 43.5, 103, 14.5, 14, 0), "lobby.inventory.navigator.item.1vs1");
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.lobbyswitcher"))) {
            switch (event.getCurrentItem().getType()) {
                case MANGROVE_BOAT, MANGROVE_CHEST_BOAT -> {
                    CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()).connect(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §x§9§1§4§C§1§D", "")));
                }
                case BARRIER -> player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_STEP, 10, 0);
            }
        }
    }

    private void teleportPlayer(Player player, Location location, String translateKey) {
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 2, 1);
        player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.5, 0)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), () -> {
            player.sendTitle(I18n.format(player, translateKey).replace("» ", ""), I18n.format(player, "lobby.title.wasteleported"), 10, 15, 10);
            player.teleport(location);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }, 20);
    }
}