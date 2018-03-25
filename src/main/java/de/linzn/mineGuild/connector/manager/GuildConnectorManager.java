/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.mineGuild.connector.manager;

import de.linzn.mineGuild.connector.GuildDatabase;
import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;

import java.util.UUID;

public class GuildConnectorManager {

    public static void set_guild_data(Guild guild) {
        MineGuildConnectorPlugin.inst().getLogger().info("Set new guild " + guild.guildName);
        GuildDatabase.addGuild(guild);
    }

    public static void set_guildplayer_data(UUID guildUUID, GuildPlayer guildPlayer) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        MineGuildConnectorPlugin.inst().getLogger().info("Set new guildplayer " + guildPlayer.getUUID().toString() + "to guild " + guild.guildName);
        guild.setGuildPlayer(guildPlayer);
    }
}
