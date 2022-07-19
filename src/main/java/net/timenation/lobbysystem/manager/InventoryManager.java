package net.timenation.lobbysystem.manager;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.lobbysystem.utils.Patch;
import net.timenation.timespigotapi.TimeSpigotAPI;
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
        player.getInventory().setItem(7, new ItemManager(Material.PLAYER_HEAD).setSkullOwner(I18n.format(player, "language.skull")).setDisplayName(I18n.format(player, "lobby.item.language")).build());
        player.getInventory().setItem(8, new ItemManager(Material.BARREL).setDisplayName(I18n.format(player, "lobby.item.gadgets")).build());
    }

    public void openNavigatorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.navigator"));

        setGlassConent(inventory);

        inventory.setItem(20, new ItemManager(Material.FIREWORK_ROCKET, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.stagebattle")).build());
        inventory.setItem(21, new ItemManager(Material.NETHERITE_AXE, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.magicpvp")).setCustomModleData(6).build());
        inventory.setItem(23, new ItemManager(Material.LIME_BED, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.bedwars")).build());
        inventory.setItem(24, new ItemManager(Material.STONE_SWORD, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.1vs1")).build());

        inventory.setItem(38, new ItemManager(Material.SADDLE).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.boxopening")).setCustomModleData(5).build());
        inventory.setItem(40, new ItemManager(Material.GLISTERING_MELON_SLICE, 1).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.spawn")).build());
        inventory.setItem(42, new ItemManager(Material.PHANTOM_MEMBRANE).setDisplayName(I18n.format(player, "lobby.inventory.navigator.item.shop")).setCustomModleData(2).build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPCPatchesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcpatches"));

        setGlassConent(inventory);

        for (Patch patch : LobbySystem.getInstance().getPatchManager().getPatches()) {
            inventory.addItem(new ItemManager(Material.WRITTEN_BOOK, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcpatches.item", patch.getPatchId()))
                    .setBookTitle("Patch Note #" + patch.getPatchId()).setBookAuthor("TimeNation System").addBookPage(I18n.format(player, "lobby.inventory.npcpatches.item", patch.getPatchId()) + "\n §8» §7" + patch.getPatchTitle() + "§r\n \n" + patch.getPatchDescription().replace("/m", "\n")).addBookPage(" \n \n \n \n    " + I18n.format(player, "lobby.inventory.npcpatches.item.patch") + " \n" + patch.getPatchLink())
                    .build());
            System.out.println(patch.getPatchDescription());
        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openLobbySwitcherInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, I18n.format(player, "lobby.inventory.lobbyswitcher"));

        for (int i = 0; i < 10; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 17; i < 27; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (ICloudService iCloudService : CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("Lobby")) {
            switch (iCloudService.getState()) {
                case VISIBLE -> {
                    if (iCloudService.getOnlineCount() == 0) {
                        inventory.addItem(new ItemManager(Material.MANGROVE_BOAT, 1).setDisplayName(I18n.format(player, "lobby.inventory.lobbyswitcher.item.lobby", (Object) iCloudService.getName())).setLore(I18n.formatLines(player, "lobby.inventory.lobbyswitcher.item.lore", iCloudService.getOnlineCount())).build());
                    } else {
                        inventory.addItem(new ItemManager(Material.MANGROVE_CHEST_BOAT, iCloudService.getOnlineCount()).setDisplayName(I18n.format(player, "lobby.inventory.lobbyswitcher.item.lobby", (Object) iCloudService.getName())).setLore(I18n.formatLines(player, "lobby.inventory.lobbyswitcher.item.lore", iCloudService.getOnlineCount())).build());
                    }
                }
                case STARTING -> inventory.addItem(new ItemManager(Material.BARRIER, 1).setCustomModleData(8).setDisplayName(I18n.format(player, "lobby.inventory.lobbyswitcher.item.lobby.starting", (Object) iCloudService.getName())).build());
            }
        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openChangeLanguageInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, I18n.format(player, "lobby.inventory.language"));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 18; i < 27; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        inventory.setItem(4, new ItemManager(Material.PLAYER_HEAD, 1).setSkullOwner(I18n.format(player, "language.skull")).setDisplayName(I18n.format(player, "lobby.inventory.language.item", (Object) I18n.format(player, "language.name"))).build());

        inventory.setItem(11, new ItemManager(Material.PLAYER_HEAD).setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").setDisplayName(I18n.format(player, "lobby.inventory.language.item", (Object) "Deutsch")).build());
        inventory.setItem(15, new ItemManager(Material.PLAYER_HEAD).setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q5MTQ1Njg3N2Y1NGJmMWFjZTI1MWU0Y2VlNDBkYmE1OTdkMmNjNDAzNjJjYjhmNGVkNzExZTUwYjBiZTViMyJ9fX0=").setDisplayName(I18n.format(player, "lobby.inventory.language.item", (Object) "English")).build());


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