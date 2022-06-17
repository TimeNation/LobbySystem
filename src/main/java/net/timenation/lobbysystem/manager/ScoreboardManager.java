package net.timenation.lobbysystem.manager;

import eu.thesimplecloud.api.CloudAPI;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.manager.scoreboard.ScoreboardBuilder;
import net.timenation.timespigotapi.player.TimePlayer;
import org.bukkit.entity.Player;

public class ScoreboardManager {

    public void sendLobbyScoreboardToPlayer(Player player) {
        ScoreboardBuilder scoreboardBuilder = new ScoreboardBuilder(player);
        TimePlayer timePlayer = TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(player);

        scoreboardBuilder.setTitle(I18n.format(player, "lobby.scoreboard.title"));

        scoreboardBuilder.setLine(0, "§8§m                            ");
        scoreboardBuilder.setLine(1, "§1");
        scoreboardBuilder.setLine(2, I18n.format(player, "lobby.scoreboard.line.lobby"));
        scoreboardBuilder.setLine(3, I18n.format(player, "lobby.scoreboard.line.lobby.info", (Object) CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()).getConnectedServerName()));
        scoreboardBuilder.setLine(4, "§2");
        scoreboardBuilder.setLine(5, I18n.format(player, "lobby.scoreboard.line.rank"));
        scoreboardBuilder.setLine(6, I18n.format(player, "lobby.scoreboard.line.rank.info", (Object) TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getHexStart().replace("GRADIENT", "SOLID"), TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getRankScoreName()));
        scoreboardBuilder.setLine(7, "§3");
        scoreboardBuilder.setLine(8, I18n.format(player, "lobby.scoreboard.line.crystals"));
        scoreboardBuilder.setLine(9, I18n.format(player, "lobby.scoreboard.line.crystals.info", timePlayer.getCrystals()));
        scoreboardBuilder.setLine(10, "§4");
        scoreboardBuilder.setLine(11, I18n.format(player, "lobby.scoreboard.line.playtime"));
        scoreboardBuilder.setLine(12, I18n.format(player, "lobby.scoreboard.line.playtime.info", (Object) TimeSpigotAPI.getInstance().getCloudManager().getOnlineTimeInDaysAndHours(CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId()))));
        scoreboardBuilder.setLine(13, "§5");
        scoreboardBuilder.setLine(14, "§8§m                            ");
    }
}