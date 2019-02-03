package de.linzn.mineGuild.connector.listener;

import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.manager.TransactionManager;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void playerJoin(final PlayerJoinEvent event) {
        TransactionManager.player_unset_prefix(event.getPlayer());
        GuildPlayer guildPlayer = GuildConnectorManager.wrap_guild_player(event.getPlayer().getUniqueId());
        if (guildPlayer != null) {
            TransactionManager.player_set_prefix(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerLeave(final PlayerQuitEvent event) {
        TransactionManager.player_unset_prefix(event.getPlayer());
    }

}
