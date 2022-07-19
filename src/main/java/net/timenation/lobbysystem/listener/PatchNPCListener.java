package net.timenation.lobbysystem.listener;

import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.manager.npc.NPC;
import net.timenation.timespigotapi.manager.npc.event.PlayerNPCInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by EinsLuca
 * Class create at 19.07.2022 09:23 @LobbySystem
 **/
public class PatchNPCListener implements Listener {

    private NPC npc;

    public PatchNPCListener(NPC npc) {
        this.npc = npc;
    }

    @EventHandler
    public void onInteract(PlayerNPCInteractEvent event) {
        if (npc.getEntityId() == event.getNPC().getEntityId()) {
            LobbySystem.getInstance().getInventoryManager().openNPCPatchesInventory(event.getPlayer());
        }
    }

}
