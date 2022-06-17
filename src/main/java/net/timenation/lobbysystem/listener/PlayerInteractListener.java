package net.timenation.lobbysystem.listener;

import net.timenation.lobbysystem.LobbySystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null || event.getItem().getItemMeta() == null || event.getItem().getType() == null || event.getItem().getItemMeta().getDisplayName() == null) return;

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            switch (event.getItem().getType()) {
                case COMPASS, HONEYCOMB, CAULDRON, TOTEM_OF_UNDYING, END_PORTAL_FRAME -> LobbySystem.getInstance().getInventoryManager().openNavigatorInventory(player);
            }
        }
    }
}
