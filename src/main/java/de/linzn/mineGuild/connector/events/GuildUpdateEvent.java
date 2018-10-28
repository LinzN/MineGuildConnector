package de.linzn.mineGuild.connector.events;

import de.linzn.mineGuild.connector.objects.Guild;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuildUpdateEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Guild guild;
    private final int level;

    public GuildUpdateEvent(final Guild guild, final int level) {
        this.guild = guild;
        this.level = level;
    }

    public static HandlerList getHandlerList() {
        return GuildUpdateEvent.handlers;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public int getLevel() {
        return this.level;
    }

    public HandlerList getHandlers() {
        return GuildUpdateEvent.handlers;
    }
}
