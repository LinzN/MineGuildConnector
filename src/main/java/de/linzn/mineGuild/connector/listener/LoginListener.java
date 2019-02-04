package de.linzn.mineGuild.connector.listener;

import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.manager.TransactionManager;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(final PlayerJoinEvent event) {
        GuildPlayer guildPlayer = GuildConnectorManager.wrap_guild_player(event.getPlayer().getUniqueId());
        if (guildPlayer != null) {
            TransactionManager.player_set_prefix(event.getPlayer());
        } else {
            if (TransactionManager.hasPrefix(event.getPlayer())) {
                TransactionManager.player_unset_prefix(event.getPlayer());
            }
        }
    }

}
