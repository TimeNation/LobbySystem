package net.timenation.lobbysystem.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class LobbyProtection implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) {
            event.setCancelled(true);
        }

        if(event.getClickedBlock() != null) {
            if(event.getClickedBlock().getType().equals(Material.STONE_BUTTON) || event.getClickedBlock().getType().equals(Material.ACACIA_TRAPDOOR) || event.getClickedBlock().getType().equals(Material.BIRCH_TRAPDOOR)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleHangingBreakByEntity(HangingBreakByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleEntityUnleash(EntityUnleashEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleItemDespawn(ItemDespawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleVillagerCareerChange(VillagerCareerChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerPickUpItem(PlayerPickupItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
