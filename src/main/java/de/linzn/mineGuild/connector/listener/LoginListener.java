package de.linzn.mineGuild.connector.listener;

import de.linzn.mineGuild.connector.manager.GuildConnectorManager;
import de.linzn.mineGuild.connector.manager.TransactionManager;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

    public void playerJoin(final PlayerJoinEvent event) {
        GuildPlayer guildPlayer = GuildConnectorManager.wrap_guild_player(event.getPlayer().getUniqueId());
        if (guildPlayer != null) {
            TransactionManager.player_set_prefix(event.getPlayer().getUniqueId());
        }
    }

    public void playerLeave(final PlayerQuitEvent event) {
        TransactionManager.player_unset_prefix(event.getPlayer().getUniqueId());
    }
}
