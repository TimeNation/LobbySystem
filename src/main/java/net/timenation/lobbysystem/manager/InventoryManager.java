package net.timenation.lobbysystem.manager;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.lobbysystem.utils.Patch;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.game.TimeGameStats;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class InventoryManager {

    private int itemCount;

    public InventoryManager(Plugin plugin) {
        this.itemCount = 0;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            switch (itemCount) {
                case 0 -> {
                    Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(0, new ItemManager(Material.COMPASS).setDisplayName(I18n.format(player, "lobby.item.navigator")).build()));
                    itemCount++;
                }
                case 1 -> {
                    Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(0, new ItemManager(Material.HONEYCOMB).setDisplayName(I18n.format(player, "lobby.item.navigator")).build()));
                    itemCount++;
                }
                case 2 -> {
                    Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(0, new ItemManager(Material.CAULDRON).setDisplayName(I18n.format(player, "lobby.item.navigator")).build()));
                    itemCount++;
                }
                case 3 -> {
                    Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(0, new ItemManager(Material.TOTEM_OF_UNDYING).setDisplayName(I18n.format(player, "lobby.item.navigator")).build()));
                    itemCount++;
                }
                case 4 -> {
                    Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setItem(0, new ItemManager(Material.END_PORTAL_FRAME).setDisplayName(I18n.format(player, "lobby.item.navigator")).build()));
                    itemCount = 0;
                }
            }
        }, 1, 80);
    }

    public void setJoinInventoryToPlayer(Player player) {
        player.getInventory().setItem(0, new ItemManager(Material.COMPASS).setDisplayName(I18n.format(player, "lobby.item.navigator")).build());
        player.getInventory().setItem(1, new ItemManager(Material.FURNACE_MINECART).setDisplayName(I18n.format(player, "lobby.item.timepass")).build());
        player.getInventory().setItem(3, new ItemManager(Material.RESPAWN_ANCHOR).setDisplayName(I18n.format(player, "lobby.item.badges")).build());
        player.getInventory().setItem(5, new ItemManager(Material.BARRIER).setDisplayName(I18n.format(player, "lobby.item.nogadget")).build());
        player.getInventory().setItem(7, new ItemManager(Material.COMPARATOR).setDisplayName(I18n.format(player, "lobby.item.settings")).build());
        player.getInventory().setItem(8, new ItemManager(Material.BARREL).setDisplayName(I18n.format(player, "lobby.item.gadgets")).build());
    }

    public void openNavigatorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.navigator"));

        setGlassConent(inventory);

        inventory.setItem(20, new ItemManager(Material.FIREWORK_ROCKET, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.stagebattle")).build());
        inventory.setItem(21, new ItemManager(Material.NETHERITE_AXE, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.magicpvp")).setCustomModleData(6).build());
        inventory.setItem(23, new ItemManager(Material.LIME_BED, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.bedwars")).build());
        inventory.setItem(24, new ItemManager(Material.STONE_SWORD, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.1vs1")).build());

        inventory.setItem(37, new ItemManager(Material.SADDLE).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.boxopening")).setCustomModleData(5).build());
        inventory.setItem(38, new ItemManager(Material.PHANTOM_MEMBRANE).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.shop")).setCustomModleData(2).build());
        inventory.setItem(40, new ItemManager(Material.GLISTERING_MELON_SLICE, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.spawn")).build());
        inventory.setItem(42, new ItemManager(Material.NETHERITE_SWORD, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.lobbypvp")).setCustomModleData(3).build());
        inventory.setItem(43, new ItemManager(Material.FISHING_ROD, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.fishing")).build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPCGamesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcgames"));

        setGlassConent(inventory);

        inventory.setItem(20, new ItemManager(Material.FIREWORK_ROCKET, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.item.stagebattle")).build());
        inventory.setItem(21, new ItemManager(Material.NETHERITE_AXE, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.item.magicpvp")).setCustomModleData(6).build());
        inventory.setItem(23, new ItemManager(Material.LIME_BED, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.item.bedwars")).build());
        inventory.setItem(24, new ItemManager(Material.STONE_SWORD, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.item.1vs1")).build());

        inventory.setItem(40, new ItemManager(Material.PAPER, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.explain")).setLore(I18n.formatLines(player, "lobby.inventory.npcgames.explain.lore")).build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPCGameInfoInventory(Player player, String game) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcgames.info", (Object) game));
        TimeGameStats timeGameStats = TimeSpigotAPI.getInstance().getTimeGameStatsManager().getTimeGameStats(game);

        setGlassConent(inventory);

        inventory.setItem(20, new ItemManager(Material.PLAYER_HEAD, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.info.item.players")).setLore(I18n.formatLines(player, "lobby.inventory.npcgames.info.lore.players", timeGameStats.getUniquePlayers(), CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName(game).getOnlinePlayerCount())).setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzUwN2Q2ZGU2MzE4MzhlN2E3NTcyMGU1YjM4ZWYxNGQyOTY2ZmRkODQ4NmU3NWQxZjY4MTJlZDk5YmJjYTQ5OSJ9fX0").build());
        inventory.setItem(22, new ItemManager(Material.KNOWLEDGE_BOOK, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.info.item.info")).setLore(I18n.formatLines(player, "game.explain." + game.toLowerCase())).build());
        inventory.setItem(24, new ItemManager(Material.PAPER, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcgames.info.item.last_update")).setLore(I18n.formatLines(player, "lobby.inventory.npcgames.info.lore.last_update", timeGameStats.getLastPatchLink())).build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPCPatchesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcpatches"));

        setGlassConent(inventory);

        for (Patch patch : LobbySystem.getInstance().getPatchManager().getPatches()) {
            inventory.addItem(new ItemManager(Material.WRITTEN_BOOK, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcpatches.item", patch.getPatchId()))
                    .setBookTitle("Patch Note #" + patch.getPatchId()).setBookAuthor("TimeNation System").addBookPage(I18n.format(player, "lobby.inventory.npcpatches.item", patch.getPatchId()) + "\n §8» §7" + patch.getPatchTitle() + "§r\n \n" + patch.getPatchDescription().replace("/m", "\n")).addBookPage(" \n \n \n \n    Link zum Patch: \n" + patch.getPatchLink())
                    .build());
            System.out.println(patch.getPatchDescription());
        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPC1vsBotInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npc1vsbot"));

        setGlassConent(inventory);

        for (ICloudService iCloudService : CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("BotPvP")) {
            if (iCloudService.getState().equals(ServiceState.VISIBLE)) {
                inventory.addItem(new ItemManager(Material.PLAYER_HEAD, 1).setDisplayName(TimeSpigotAPI.getInstance().getColorAPI().process("<SOLID:2267d6>» BotPvP-" + iCloudService.getServiceNumber())).setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FiNTI3ZDE5Nzg1MTE3YzZhN2I2NjU1M2M2ZjBlMjg3MzU3NjNhM2JlNDExM2ZjYmNhNmM0N2UzNmI0NzViMCJ9fX0=").build());
            }
        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    private void setGlassConent(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }
    }
}