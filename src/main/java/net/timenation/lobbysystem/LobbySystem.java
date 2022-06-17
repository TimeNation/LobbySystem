package net.timenation.lobbysystem;

import lombok.Getter;
import net.timenation.lobbysystem.listener.InventoryClickListener;
import net.timenation.lobbysystem.listener.LobbyProtection;
import net.timenation.lobbysystem.listener.PlayerInteractListener;
import net.timenation.lobbysystem.listener.PlayerJoinListener;
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
    }

    public static LobbySystem getInstance() {
        return instance;
    }
}
