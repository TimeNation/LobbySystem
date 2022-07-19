package net.timenation.lobbysystem.utils;

import eu.thesimplecloud.api.CloudAPI;
import net.timenation.lobbysystem.LobbySystem;
import net.timenation.lobbysystem.listener.PatchNPCListener;
import net.timenation.lobbysystem.manager.ServerInventory;
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

    private final NPCManager patchnotesNPC;
    private final NPCManager botPvPNPC;
    private final NPCManager playerPvPNPC;
    private final NPCManager stageBattleNPC;

    public NPCs(Plugin plugin) {
        patchnotesNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 2.5, 101, -5.5), UUID.randomUUID(), "<GRADIENT:60a123>» Patch Notes</GRADIENT:61d651>", false, null, "ewogICJ0aW1lc3RhbXAiIDogMTU5MTkwMjA2OTUyNCwKICAicHJvZmlsZUlkIiA6ICJhNzFjNTQ5MmQwNTE0ZDg3OGFiOTEwZmRmZmRmYzgyZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcHBsZTU0NDciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA3NWMyM2VlMzA2NzM0YzQ1MGU5MjQyYmJmYjgzMDljZGNlOTI4NjNhODAzNzYzYzNmOGM1NmZiOTljMDVkYyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9", "xnwJakS46QfJ6upXZieMdrE+QbxgfsuJQEgsrvf3IMSmkRiKxqz8xVj6HNrZpqsvY8YtuipDTl8uTLfd0/s+CKrsx1yStYGolg72+ZYL/kj5j4zSe/+TwXOHNlVJr88ZOXmP03UmSjwewHDUGSz3awb8QFWSdrq8WKE+jjr1THZ5zfmIqpFLRlNYLDRsq3+igrrvbDdjFKIR4eA6p+SLbCf15JqH+yRfPAMfCXu1QEV7wFT0FRhp5iq1A3eeBb2L/9KJf9/V/sZmr/oD1EbCSFV9lkB2iVVT/5NiIWTooTgi3UcoKoON3h6rCBUL7Xml4WDiOjpaEMx6HLVLrBTKTCM9YromyvdofOPKxXvaQ/A++EbRYVO8GWCbY1jmxfyEhkeimo1/PG0X8qDGxEvsIp9e22iMka6nU5MddVXrDxRdwG1X2SeH6q0xA91QcO4YNmCgtc90VwGOFB7ksJ43E8WaDJoTeHzOjdQluEFTjvGvVCgwLi84uN9n1qKauukml1Ek8xqaivOAYn/lmA5QTT+magN1iKAjtQ4Gz7EI1hDITfVHOc3z9/hWvAsgNAU7GbK9negtS2JIdfTKJPTjcL4DLoyZor/zJdTLPNKvpHg6ywXp33emeJLxqcGbsr2ER+RUEluW63vsfMs39Sly1nNh3ZZZHRYUA7GH0K5tsw8=");
        botPvPNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 44.5, 103, 20.5), UUID.randomUUID(), "<GRADIENT:2267d6>» BotPvP</GRADIENT:598cde>", true, CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("BotPvP"), "ewogICJ0aW1lc3RhbXAiIDogMTYxMTkyMDkzNzQ0OSwKICAicHJvZmlsZUlkIiA6ICI5ZDQyNWFiOGFmZjg0MGU1OWM3NzUzZjc5Mjg5YjMyZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb21wa2luNDIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RjNjM4NTc4MDNiNmNhOWIwZDEyYTNiNzJhMmIyMGQ4M2EyYWRkNmYyZjg3OTNlMGVkYmI0ODIwY2JhMDUyMiIKICAgIH0KICB9Cn0=", "EyvEEKqQ3YV3cO9cShVfffLvDxz3kxNbRzl2gg6zEMHca9kjl2rl7O56LVSo0YKnaDMUhaGE6ETWaXZxN8oxNFELi++jkvJ7GAzxX93hjHeF8NTFAVyeYXx9yfA1Vr0sR6ykA5bwVzp9niVL2/FxhLpyIPvqOK+aXmu7/3ZNWkBlUbSkICRr3s2Ftl4HAIgm1cIVAHGW8OMJw5mA/8GYfnr8A45hx3olU9VyE4v1PydHZcvuN5Fp+s6UOshHd7kRMSb0yUdOLMA75X9BRstBkpfqDbhHMMYrpn3WxLEN09Ccr7QmqLS8QVpDKvSwxVpEL+lqTMGUVtJ3ocduteZ/KTJ4nSuksVXXUl1pY0bIUZ8lKpJvijIQE1Qogdg8rHoW3JqYBzMcc6xnUGK6/KPrauLLw2v58XA3n4HsFqIUQorsDdD+PWjnhikVAjP29IVr3hBjQy7AoxwOpOEzkcehPWlRfpsKcxU84rkgyZaaPnO/5DRIQPfdraFgtilxxtQ+I+dKxeUH4qquxBJyUjRbG8kBBRSERKImgQYEKgQqzdzseHIS+dRbiZIkGkujoNCd5NvkkTXv13jkTZ5TdEfpoZ0dHfVqiVRPk+LwGhQoCotkp+gLsv9ormRPgn3Bbpe4nT+C4RhnDr541xx9YTlPMgCsCA4pmv+LVoDRq8QrKt0=");
        playerPvPNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), 38.5, 103, 18.5), UUID.randomUUID(), "<GRADIENT:dbad2e>» 1vs1</GRADIENT:dbc42e>", true, CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("1vs1"), "ewogICJ0aW1lc3RhbXAiIDogMTYyMjIyOTAwOTA2MiwKICAicHJvZmlsZUlkIiA6ICJmNmYzYTUzMDZjMzk0MDk4OTZhMDZiZGY0ZjNhZmM3MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCYXN0aUdIRyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWQ5MjJhNTVlNzE1YTQzZWNmOGM5MjE0Nzk2NGYzZTNiZWIyZGJiYjBkYWYwMTVjZTg4ZDg5NmUxOTEzMjM5IgogICAgfQogIH0KfQ==", "pBYZSHtX1TkNwcWKf2AcslhHuBhLaRPSRqCwp0I5QYu+MtgUYlLCkTtXZ3z1tzU59fj2cUbJ8WTwJqK2hDry9iJQmPrBgjTLTFbFn+PFyqtxT5GMc+x32xurNBX6a5QmPtZDoMxeiAwMbBEoL4wJ9JlWh4Eabijnbq84nCeXaZjdk6NJTj5p7E3enJ417efx/qYiguGvabjzT+wcgtqhePneqktfI01hoqiJRO7i/2jduEs21uDb2shVhOcqLPRAOYIsZ5e6aHAzBaelMLZT6dZSc8ppZaMOK+29u2XbWIYR/pqXNWdDjd7/HvrDTjriVAZji3zJTbunTcfWpSF/6vi3ELV9JTTSmmVQXRgkc/JbCqFQ2bmL8OufirsJeVp1J8VPJGSXuGP45LqCcwGGN7FqKxkWP9gLeRDaH6wXSYwpUm/3kpjcX29D0uWvBY7/DFvkcFbF4SoqfEelbQrsu5VBu1PP5kYcJKUMBVzEoTynpn5u6MZcf2x/5xuE79pbEpdtJeaj88fJXM4bdoP+OeGBfTxE4Gx8pQ1fhn5Qn1doCTHUGMoKXAOnYa8K3bkfXQw+ne4LPSdGiFEphwiASOIFkSuIu+wOgWE3eyxYuJtNgoa0H1Uk4lMenC1QaZN0I+TpoCiWEHfUlqcndS7Dy6yGcgJsmLhiiuJcgbFou5k=");
        stageBattleNPC = new NPCManager(plugin, new Location(Bukkit.getWorld("world"), -39.5, 99, -8.5), UUID.randomUUID(), "<GRADIENT:16de37>» StageBattle</GRADIENT:49c45d>", true, CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName("StageBattle"), "ewogICJ0aW1lc3RhbXAiIDogMTY1NjYyNTQzMDQyNywKICAicHJvZmlsZUlkIiA6ICIwNDNkZWIzOGIyNjE0MTg1YTIzYzU4ZmI2YTc5ZWZkYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaXRhbFNpZ256MiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mMmFhODFhZDUwZTRhMzM2ZTFlMzBhNjlkMTM2YTk3M2I4Y2Y0NTI4ZDUyNzA4MGY3MzQ2YmRkYzRiN2FlYWU5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=", "g8IeiPUzAI4izJ0vU2NgUblcfnGfvHedU5ZmZ2NSavCNpFJHd53cdSZWdX6JUF7/17GSmLigutv6go4xSonrxhXqZ+9MUzBtEv8E11JH+HW2Rmoke1wi5MCtX3c47+lxJkI//qxF4yrbUk8Yo+0Q9vthsvtHAig1j3Dkty4pX6lHO8+hyWmnQ4YdhkznHDeupsF4m5ux7yGLds47IDGEYT7QIEjCevZPAGnWoh/efg3Ro2UGZLgScoh0PRQPOdvOfSXXBKpFX3UhnrvBwyZQE5zzhUHGPJz+l2mwQpINX/xbqBE/iH0Sue2cRzlci5hdjKeN808PKl94ypXi1j1f54QzYanQZtbHfZk+VcjYPz4IaWPktlk8C55hfrO9kk71L3ICEi9/501mbJ+KvGScXP+5rLzmjepopfg0J8AUKZe6OGzFgqZXa/MZT/EsndtnqdXt5+LGkyxP7E8VjdS2jaF3P2nKZmNWtxDH9g0hI6zqcYq3I+306B/b1YQ2r11gMXmNfn6TPWXNLFoOtg1mH6xJ9xkg1hBzMHMIjgy43U849UCKRU3xhaGSJbDmgnqq10i1+H5OLHu3bLwIzTHmRxQ3Q278S0F0b1qrSYn/j1xMpu64xDjBtFcDlK8TEb3lx5aWsnbSGujVLovGCR4a3oKrGej8gwgfn1R4hqGgQxE=");

        new ServerInventory(botPvPNPC, "BotPvP", "2267d6", "598cde");
        new ServerInventory(playerPvPNPC, "1vs1", "dbad2e", "dbc42e");
        new ServerInventory(stageBattleNPC, "StageBattle", "16de37", "49c45d");

        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getPluginManager().registerEvents(new PatchNPCListener(patchnotesNPC.getNpc()), plugin);
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null)
            return;

        if (event.getView().getTitle().equals(I18n.format(player, "lobby.inventory.npcpatches")) && event.getCurrentItem().getType().equals(Material.WRITTEN_BOOK))
            player.openBook(event.getCurrentItem());
    }
}
