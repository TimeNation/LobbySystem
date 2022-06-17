package net.timenation.lobbysystem.listener;

import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AtomicInteger count = new AtomicInteger(1);

        player.teleport(new Location(Bukkit.getWorld("world"), 56.5, 20, 5.5, 90, 0));
        player.sendTitle(I18n.format(player, "title_one_top"), I18n.format(player, "title_one_bottom", (Object) TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersNameWithRankColor(player.getUniqueId())));
        player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 2, 1);
        player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1.5, 0)));
        player.teleport(new Location(Bukkit.getWorld("world"), 56.5, 20, 5.5, 90, 0));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 2);
        player.getInventory().clear();
        LobbySystem.getInstance().getScoreboardManager().sendLobbyScoreboardToPlayer(player);
        LobbySystem.getInstance().getInventoryManager().setJoinInventoryToPlayer(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(LobbySystem.getInstance(), () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    switch (count.get()) {
                        case 1 -> {
                            player.sendTitle(I18n.format(player, "title_two_top"), I18n.format(player, "title_two_bottom"));
                            count.getAndIncrement();
                        }
                        case 2 -> {
                            player.sendTitle(I18n.format(player, "title_three_top"), I18n.format(player, "title_three_bottom"));
                            count.getAndIncrement();
                            cancel();
                        }
                    }
                }
            }.runTaskTimerAsynchronously(LobbySystem.getInstance(), 1, 30);
        }, 60);
    }
}
