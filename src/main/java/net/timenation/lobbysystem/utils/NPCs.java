package net.timenation.lobbysystem.utils;

import eu.thesimplecloud.api.CloudAPI;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.manager.npc.NPCManager;
import net.timenation.timespigotapi.manager.npc.event.PlayerNPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.N;

import java.util.UUID;

public class NPCs implements Listener {

    private final NPCManager gamemodesNPC;
    private final NPCManager patchnotesNPC;
    private final NPCManager famousGamemodeNPC;
    private final NPCManager botPvPNPC;
    private final NPCManager playerPvPNPC;

    public NPCs(Plugin plugin) {
        gamemodesNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 41.5, 21, 5.5, -90, 0), UUID.randomUUID(), "<GRADIENT:eb4c34>» Spielmodi</GRADIENT:ed922b>", false, null, "ewogICJ0aW1lc3RhbXAiIDogMTY1MzMyMDU0OTE4OCwKICAicHJvZmlsZUlkIiA6ICJjMTY4NTcyODcyZDY0ZGJlODg5OTI4YzRhYTNjYjkzYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCeVJhdWR5IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzgwYWQzMmE4YzAxNWI4YjYxNTM3NTI4MDY1NzQ5NmFhZGIwNjgzZjdhZjVlMzEzYzNhOGZiZTczNzIzNmIyNzMiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==", "dl68XSdM7Q03tl4FTcBMjK9emL3HMeZ7LSnG1V/BrJY2gX1Gv9Q8jby0FP55l4fuLTm7QK2WYhLGa6Bu4THCU6lIXl7AWCR3o930oSy8jirAcij3/gzefaraFXQdCf0BqSP3D+6sMYsH0nqRPD+BmjK7cAKCSFlhfx9azgvz5RFAwbPbF3qh9EolXJc3BbAoOrN7dJKur/2Hvlssbf9LT5qx+MONiCSjYxKwp/ctPt5q9F8GurIKIuLJsZZQUqAzKd+DaAMLKPSavKk+BeP2xBzbP4ndhq7NLLNzrkbb+/YEG4yP06uuIOkmATmzL1v+0zYv3U/HE2QMs+Q5HscDD4jt9to+O/PPOfuSu2LuphWgslqVHxMnFfTXbsxhmG5ztWoh4RdJtTIAD1ahKKRV0Y7m5UPdMEQg+666NPoX3ASfvWuxD9hhNtQaMuf5XAxpEffg+ihSJWf2yjAs4MMRxGwztuf8ruSGzVHkaSU/bwiBkTTUD7q2tr3Rt7OdOOgMgdVxhoTMQC22EzHDfEpWHJOkIGPhx0KsTYDB3tGArhq8ZvnHBw/0yeewnl9yighzjOt4QWmUPfORfSlFLb6aGbxzSv/Y9//K/nC9Ii9PYNHVBuw/EmIP+hd6eLQFdgiQF/Rc7IKqII5MmK2L/hCFF+XFCAl8WmgvSZIVtbQ7xHg=");
        patchnotesNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 42.5, 21, 9.5, -90, 0), UUID.randomUUID(), "<GRADIENT:60a123>» Patch Notes</GRADIENT:61d651>", false, null, "ewogICJ0aW1lc3RhbXAiIDogMTU5MTkwMjA2OTUyNCwKICAicHJvZmlsZUlkIiA6ICJhNzFjNTQ5MmQwNTE0ZDg3OGFiOTEwZmRmZmRmYzgyZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcHBsZTU0NDciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA3NWMyM2VlMzA2NzM0YzQ1MGU5MjQyYmJmYjgzMDljZGNlOTI4NjNhODAzNzYzYzNmOGM1NmZiOTljMDVkYyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9", "xnwJakS46QfJ6upXZieMdrE+QbxgfsuJQEgsrvf3IMSmkRiKxqz8xVj6HNrZpqsvY8YtuipDTl8uTLfd0/s+CKrsx1yStYGolg72+ZYL/kj5j4zSe/+TwXOHNlVJr88ZOXmP03UmSjwewHDUGSz3awb8QFWSdrq8WKE+jjr1THZ5zfmIqpFLRlNYLDRsq3+igrrvbDdjFKIR4eA6p+SLbCf15JqH+yRfPAMfCXu1QEV7wFT0FRhp5iq1A3eeBb2L/9KJf9/V/sZmr/oD1EbCSFV9lkB2iVVT/5NiIWTooTgi3UcoKoON3h6rCBUL7Xml4WDiOjpaEMx6HLVLrBTKTCM9YromyvdofOPKxXvaQ/A++EbRYVO8GWCbY1jmxfyEhkeimo1/PG0X8qDGxEvsIp9e22iMka6nU5MddVXrDxRdwG1X2SeH6q0xA91QcO4YNmCgtc90VwGOFB7ksJ43E8WaDJoTeHzOjdQluEFTjvGvVCgwLi84uN9n1qKauukml1Ek8xqaivOAYn/lmA5QTT+magN1iKAjtQ4Gz7EI1hDITfVHOc3z9/hWvAsgNAU7GbK9negtS2JIdfTKJPTjcL4DLoyZor/zJdTLPNKvpHg6ywXp33emeJLxqcGbsr2ER+RUEluW63vsfMs39Sly1nNh3ZZZHRYUA7GH0K5tsw8=");
        famousGamemodeNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 42.5, 21, 1.5, -90, 0), UUID.randomUUID(), "<GRADIENT:b814a9>» Aktuell beliebt</GRADIENT:e65ec8>", false, null, "ewogICJ0aW1lc3RhbXAiIDogMTYyMjQ4MTg5MjAyOSwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk2YWYxNDY4NzE2MzJhNjgwYzMwYzlkNTlkMDI1NzkyYWZjZDJkOTU2ZGU4ZDY2OGUzNjM1ZjE4YmE3OTg2NmMiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "eZF6+i11v7+is+LWU6J138OAHjh3oPNvOHYh6mG4ImvX/GMJv8/EgAq12ZSbx7EjIVl9sCdJKDpOEPf5U/2msAFqoNSlCz9ibc+9oiyUyvocU5si9khl1VFZnBucPYFu5plDqyd9Y8jWMrTd8jD6plPgbOXuGKiQ324GaVSFMQfPI/6qPm6l6bNZK9Wqh5xeohbLAFA+wX3EVJJZJMm0Yz6ptdhEJDRjy+JBmeDyt9cnXO7NsnRCAMC1CL7qxRhtABgFFMBJg5vBkZlVRk/lA7ViJowvaK44uNLiYpo8WQHVLkqKh0Fkav/QvfYZ6lnOTFyqR3kgjFzqYtj13ToZfeQo71cThWYw9qU8pSDm38IBIvU+MEdsDYwZcPggqsFGUC2xfX79BQdQ8micHTpvMNuYjJEwtaiEWOl2ipLhoXdp6ZszeKwY3sWlS3qxW9Hw57SHrhACCm5A8PO/kiXDzAYQkbDWhhrYdeMcsjS3AfrFtGxUSY/rtzC83iwH18YOZ+FbUF8g9NLFrV81C1WTMHuuthSiZrWiml7xoVRdk913qe/Jp+U5UgJA2tAH6DiV2srmC+LM4CUqD6lSJe8ybJdYtWUaS4Kl68hgeSHwYPFxiTTllayrpYO0gUpxSmBnM7efXpFFkkzz/hqVWQdPuaYeQfojOyLzaVZCRREedfM=");
        botPvPNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 65.5, 26, 69.5), UUID.randomUUID(), "<GRADIENT:2267d6>» BotPvP</GRADIENT:598cde>", true, CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("BotPvP"), "ewogICJ0aW1lc3RhbXAiIDogMTYxMTkyMDkzNzQ0OSwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RjNjM4NTc4MDNiNmNhOWIwZDEyYTNiNzJhMmIyMGQ4M2EyYWRkNmYyZjg3OTNlMGVkYmI0ODIwY2JhMDUyMiIKICAgIH0KICB9Cn0=", "EyvEEKqQ3YV3cO9cShVfffLvDxz3kxNbRzl2gg6zEMHca9kjl2rl7O56LVSo0YKnaDMUhaGE6ETWaXZxN8oxNFELi++jkvJ7GAzxX93hjHeF8NTFAVyeYXx9yfA1Vr0sR6ykA5bwVzp9niVL2/FxhLpyIPvqOK+aXmu7/3ZNWkBlUbSkICRr3s2Ftl4HAIgm1cIVAHGW8OMJw5mA/8GYfnr8A45hx3olU9VyE4v1PydHZcvuN5Fp+s6UOshHd7kRMSb0yUdOLMA75X9BRstBkpfqDbhHMMYrpn3WxLEN09Ccr7QmqLS8QVpDKvSwxVpEL+lqTMGUVtJ3ocduteZ/KTJ4nSuksVXXUl1pY0bIUZ8lKpJvijIQE1Qogdg8rHoW3JqYBzMcc6xnUGK6/KPrauLLw2v58XA3n4HsFqIUQorsDdD+PWjnhikVAjP29IVr3hBjQy7AoxwOpOEzkcehPWlRfpsKcxU84rkgyZaaPnO/5DRIQPfdraFgtilxxtQ+I+dKxeUH4qquxBJyUjRbG8kBBRSERKImgQYEKgQqzdzseHIS+dRbiZIkGkujoNCd5NvkkTXv13jkTZ5TdEfpoZ0dHfVqiVRPk+LwGhQoCotkp+gLsv9ormRPgn3Bbpe4nT+C4RhnDr541xx9YTlPMgCsCA4pmv+LVoDRq8QrKt0=");
        playerPvPNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 65.5, 26, 74.5), UUID.randomUUID(), "<GRADIENT:dbad2e>» 1vs1</GRADIENT:dbc42e>", true, CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("1vs1"), "ewogICJ0aW1lc3RhbXAiIDogMTYyMjIyOTAwOTA2MiwKICAicHJvZmlsZUlkIiA6ICJmNmYzYTUzMDZjMzk0MDk4OTZhMDZiZGY0ZjNhZmM3MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCYXN0aUdIRyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWQ5MjJhNTVlNzE1YTQzZWNmOGM5MjE0Nzk2NGYzZTNiZWIyZGJiYjBkYWYwMTVjZTg4ZDg5NmUxOTEzMjM5IgogICAgfQogIH0KfQ==", "pBYZSHtX1TkNwcWKf2AcslhHuBhLaRPSRqCwp0I5QYu+MtgUYlLCkTtXZ3z1tzU59fj2cUbJ8WTwJqK2hDry9iJQmPrBgjTLTFbFn+PFyqtxT5GMc+x32xurNBX6a5QmPtZDoMxeiAwMbBEoL4wJ9JlWh4Eabijnbq84nCeXaZjdk6NJTj5p7E3enJ417efx/qYiguGvabjzT+wcgtqhePneqktfI01hoqiJRO7i/2jduEs21uDb2shVhOcqLPRAOYIsZ5e6aHAzBaelMLZT6dZSc8ppZaMOK+29u2XbWIYR/pqXNWdDjd7/HvrDTjriVAZji3zJTbunTcfWpSF/6vi3ELV9JTTSmmVQXRgkc/JbCqFQ2bmL8OufirsJeVp1J8VPJGSXuGP45LqCcwGGN7FqKxkWP9gLeRDaH6wXSYwpUm/3kpjcX29D0uWvBY7/DFvkcFbF4SoqfEelbQrsu5VBu1PP5kYcJKUMBVzEoTynpn5u6MZcf2x/5xuE79pbEpdtJeaj88fJXM4bdoP+OeGBfTxE4Gx8pQ1fhn5Qn1doCTHUGMoKXAOnYa8K3bkfXQw+ne4LPSdGiFEphwiASOIFkSuIu+wOgWE3eyxYuJtNgoa0H1Uk4lMenC1QaZN0I+TpoCiWEHfUlqcndS7Dy6yGcgJsmLhiiuJcgbFou5k=");

        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            TimeSpigotAPI.getInstance().getTimeGameStatsManager().updateTimeGameStatsForPlugin("StageBattle");
            TimeSpigotAPI.getInstance().getTimeGameStatsManager().updateTimeGameStatsForPlugin("MagicPvP");
            TimeSpigotAPI.getInstance().getTimeGameStatsManager().updateTimeGameStatsForPlugin("BedWars");
            TimeSpigotAPI.getInstance().getTimeGameStatsManager().updateTimeGameStatsForPlugin("1vs1");
        }, 1, 1200);
    }

    @EventHandler
    public void handlePlayerNPCInteract(PlayerNPCInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getHand().equals(PlayerNPCInteractEvent.Hand.MAIN_HAND) && event.getUseAction().equals(PlayerNPCInteractEvent.EntityUseAction.INTERACT)) {
            if (event.getNPC().getEntityId() == gamemodesNPC.getNpc().getEntityId())
                LobbySystem.getInstance().getInventoryManager().openNPCGamesInventory(player);

            if (event.getNPC().getEntityId() == patchnotesNPC.getNpc().getEntityId())
                LobbySystem.getInstance().getInventoryManager().openNPCPatchesInventory(player);

            if (event.getNPC().getEntityId() == botPvPNPC.getNpc().getEntityId())
                LobbySystem.getInstance().getInventoryManager().openNPC1vsBotInventory(player);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null)
            return;

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npcgames"))) {
            switch (event.getSlot()) {
                case 20 -> LobbySystem.getInstance().getInventoryManager().openNPCGameInfoInventory(player, "StageBattle");
                case 21 -> LobbySystem.getInstance().getInventoryManager().openNPCGameInfoInventory(player, "MagicPvP");
                case 23 -> LobbySystem.getInstance().getInventoryManager().openNPCGameInfoInventory(player, "BedWars");
                case 24 -> LobbySystem.getInstance().getInventoryManager().openNPCGameInfoInventory(player, "1vs1");
            }
        }

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npcpatches")) && event.getCurrentItem().getType().equals(Material.WRITTEN_BOOK))
            player.openBook(event.getCurrentItem());
    }
}
