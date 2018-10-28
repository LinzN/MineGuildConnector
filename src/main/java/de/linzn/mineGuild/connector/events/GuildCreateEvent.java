package de.linzn.mineGuild.connector.events;

import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuildCreateEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Guild guild;
    private final String sourceServer;

    public GuildCreateEvent(final Guild guild, final String sourceServer) {
        this.guild = guild;
        this.sourceServer = sourceServer;
    }

    public static HandlerList getHandlerList() {
        return GuildCreateEvent.handlers;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public boolean isSourceServer() {
        return MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME.equalsIgnoreCase(this.sourceServer);
    }

    public HandlerList getHandlers() {
        return GuildCreateEvent.handlers;
    }
}
