package net.timenation.lobbysystem.manager;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.manager.npc.NPCManager;
import net.timenation.timespigotapi.manager.npc.event.PlayerNPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Locale;

public class ServerInventory implements Listener {

    private final NPCManager npc;
    private final String cloudGroupName;
    private final String hexCode;
    private final String hexEnd;
    private final String hexForMinecraft;

    public ServerInventory(NPCManager npc, String cloudGroupName, String hexCode, String hexEnd) {
        this.npc = npc;
        this.cloudGroupName = cloudGroupName;
        this.hexCode = hexCode;
        this.hexEnd = hexEnd;
        this.hexForMinecraft = TimeSpigotAPI.getInstance().getColorAPI().process("<SOLID:" + hexCode + ">");

        Bukkit.getPluginManager().registerEvents(this, LobbySystem.getInstance());
    }

    @EventHandler
    public void handlePlayerNPCInteract(PlayerNPCInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getHand().equals(PlayerNPCInteractEvent.Hand.MAIN_HAND) && event.getUseAction().equals(PlayerNPCInteractEvent.EntityUseAction.INTERACT) && event.getNPC().getEntityId() == npc.getNpc().getEntityId()) {
            openNPCServerSelectionInventory(player, cloudGroupName, hexCode, hexEnd);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npcserver", TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:" + hexCode + ">" + cloudGroupName + " »</GRADIENT:" + hexEnd + "> ")))) {

            switch (event.getCurrentItem().getType()) {
                case MANGROVE_BOAT, MANGROVE_CHEST_BOAT -> CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()).connect(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» " + hexForMinecraft.toUpperCase().replace("X", "x"), "")));
                case SPYGLASS -> openNPCSpectateServerSelectionInventory(player, cloudGroupName, hexCode, hexEnd);
                case BARRIER -> player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_STEP, 10, 0);
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npcserver.spectate", TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:" + hexCode + ">" + cloudGroupName + " »</GRADIENT:" + hexEnd + "> ")))) {
            switch (event.getCurrentItem().getType()) {
                case SPYGLASS -> CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()).connect(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» " + hexForMinecraft.toUpperCase().replace("X", "x"), "")));
                case BARRIER -> openNPCServerSelectionInventory(player, cloudGroupName, hexCode, hexEnd);
            }
        }
    }

    public void openNPCServerSelectionInventory(Player player, String cloudGroupName, String hexCode, String hexEnd) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcserver", TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:" + hexCode + ">" + cloudGroupName + " »</GRADIENT:" + hexEnd + "> ")));

        setGlassConent(inventory);

        for (int i = 9; i < 19; i++) {
            inventory.setItem(i, new ItemManager(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 26; i < 36; i++) {
            inventory.setItem(i, new ItemManager(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (ICloudService iCloudService : CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName(cloudGroupName)) {
            switch (iCloudService.getState()) {
                case VISIBLE -> {
                    if (iCloudService.getOnlineCount() == 0) {
                        inventory.addItem(new ItemManager(Material.MANGROVE_BOAT, 1).setDisplayName(TimeSpigotAPI.getInstance().getColorAPI().process("§8» <SOLID:" + hexCode + ">" + cloudGroupName + "-" + iCloudService.getServiceNumber())).setLore(I18n.formatLines(player, "lobby.inventory.lobbyswitcher.item.lore", iCloudService.getOnlineCount())).build());
                    } else {
                        inventory.addItem(new ItemManager(Material.MANGROVE_CHEST_BOAT, iCloudService.getOnlineCount()).setDisplayName(TimeSpigotAPI.getInstance().getColorAPI().process("§8» <SOLID:" + hexCode + ">" + cloudGroupName + "-" + iCloudService.getServiceNumber())).setLore(I18n.formatLines(player, "lobby.inventory.lobbyswitcher.item.lore", iCloudService.getOnlineCount())).build());
                    }
                }
                case STARTING ->
                        inventory.addItem(new ItemManager(Material.BARRIER, 1).setCustomModleData(8).setDisplayName(TimeSpigotAPI.getInstance().getColorAPI().process("§8» <SOLID:" + hexCode + ">" + cloudGroupName + "-" + iCloudService.getServiceNumber() + " §8(<SOLID:a32e2e>STARTING§8)")).build());
            }
        }

        inventory.setItem(40, new ItemManager(Material.SPYGLASS, 1).setDisplayName(I18n.format(player, "lobby.inventory.npcserver.spectate.item", (Object) hexCode)).build());

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 100, 2);
    }

    public void openNPCSpectateServerSelectionInventory(Player player, String cloudGroupName, String hexCode, String hexEnd) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "lobby.inventory.npcserver.spectate", TimeSpigotAPI.getInstance().getColorAPI().process("<GRADIENT:" + hexCode + ">" + cloudGroupName + " »</GRADIENT:" + hexEnd + "> ")));

        setGlassConent(inventory);

        for (int i = 9; i < 19; i++) {
            inventory.setItem(i, new ItemManager(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 26; i < 36; i++) {
            inventory.setItem(i, new ItemManager(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (ICloudService iCloudService : CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName(cloudGroupName)) {
            switch (iCloudService.getState()) {
                case INVISIBLE -> {
                    inventory.addItem(new ItemManager(Material.SPYGLASS, iCloudService.getOnlineCount()).setDisplayName(TimeSpigotAPI.getInstance().getColorAPI().process("§8» <SOLID:" + hexCode + ">" + cloudGroupName + "-" + iCloudService.getServiceNumber())).setLore(I18n.formatLines(player, "lobby.inventory.lobbyswitcher.item.lore", iCloudService.getOnlineCount())).build());
                }
            }
        }

        inventory.setItem(40, new ItemManager(Material.BARRIER, 1).setCustomModleData(2).setDisplayName(I18n.format(player, "lobby.inventory.npcserver.spectate.back")).build());

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
