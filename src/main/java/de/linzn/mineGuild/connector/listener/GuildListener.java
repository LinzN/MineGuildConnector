package de.linzn.mineGuild.connector.listener;

import de.linzn.mineGuild.connector.events.GuildUpdateEvent;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import de.linzn.mineLib.title.MineTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GuildListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void guildLevelUp(GuildUpdateEvent event) {
        int level = event.getLevel();
        Guild guild = event.getGuild();
        for (GuildPlayer guildPlayer : guild.guildPlayers) {
            Player player = Bukkit.getServer().getPlayer(guildPlayer.getUUID());
            if (player != null) {
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, SoundCategory.MASTER, 15, 5);
                new MineTitle("" + ChatColor.GREEN + ChatColor.BOLD + "Gilden Levelup", "" + ChatColor.YELLOW + ChatColor.BOLD + "Level: " + ChatColor.RED + ChatColor.BOLD + level, 5, 40, 20).send(player);
            }
        }
    }
}
