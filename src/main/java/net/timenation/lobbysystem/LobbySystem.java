package net.timenation.lobbysystem;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import net.timenation.lobbysystem.listener.*;
import net.timenation.lobbysystem.manager.InventoryManager;
import net.timenation.lobbysystem.manager.PatchManager;
import net.timenation.lobbysystem.manager.ScoreboardManager;
import net.timenation.lobbysystem.utils.NPCs;
import net.timenation.timespigotapi.manager.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LobbySystem extends JavaPlugin {

    private static LobbySystem instance;
    private InventoryManager inventoryManager;
    private ScoreboardManager scoreboardManager;
    private PatchManager patchManager;

    @Override
    public void onEnable() {
        instance = this;
        inventoryManager = new InventoryManager(this);
        scoreboardManager = new ScoreboardManager();
        patchManager = new PatchManager();

        new NPCs(this);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new LobbyProtection(), this);

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            int players = CloudAPI.getInstance().getCloudServiceGroupManager().getProxyGroupByName("Proxy").getOnlinePlayerCount();
            int slots = CloudAPI.getInstance().getCloudServiceGroupManager().getProxyGroupByName("Proxy").getMaxPlayers();

            Bukkit.getOnlinePlayers().forEach(current -> {
                current.setLevel(players);
                current.setExp((float)players / (float)slots > 1 ? 1  : (float)players / (float)slots);
            });
        },0, 5);
    }

    public static LobbySystem getInstance() {
        return instance;
    }
}
