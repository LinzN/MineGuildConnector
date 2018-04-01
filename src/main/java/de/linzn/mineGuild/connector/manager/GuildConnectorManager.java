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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class GuildConnectorManager {

    public static void add_guild(Guild guild, UUID creator) {
        GuildDatabase.addGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Added new guild " + guild.guildUUID.toString());
        add_guildplayer(guild.guildUUID, creator);
    }

    public static void remove_guild(UUID guildUUID) {
        GuildDatabase.removeGuild(guildUUID);
        MineGuildConnectorPlugin.inst().getLogger().info("Removed guild " + guildUUID.toString());
    }

    public static void update_guild(UUID guildUUID, int level) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        guild.setLevel(level);
        MineGuildConnectorPlugin.inst().getLogger().info("Updated guild " + guild.guildUUID.toString());
    }

    public static void add_guildplayer(UUID guildUUID, UUID playerUUID) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        GuildPlayer guildPlayer = new GuildPlayer(playerUUID);
        guild.setGuildPlayer(guildPlayer);
        guildPlayer.setGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Add new guildplayer " + guildPlayer.getUUID().toString() + " to guild " + guild.guildUUID.toString());
    }

    public static void remove_guildplayer(UUID guildUUID, UUID playerUUID) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        GuildPlayer guildPlayer = GuildDatabase.getGuildPlayer(playerUUID);
        guild.unsetGuildPlayer(guildPlayer);
        MineGuildConnectorPlugin.inst().getLogger().info("Removed new guildplayer " + playerUUID.toString() + " from guild " + guild.guildUUID.toString());
    }

    public static void set_guild_data(Guild guild) {
        GuildDatabase.addGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Set new guild " + guild.guildUUID.toString());
    }

    public static void set_guildplayer_data(UUID guildUUID, UUID playerUUID) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        GuildPlayer guildPlayer = new GuildPlayer(playerUUID);
        guild.setGuildPlayer(guildPlayer);
        guildPlayer.setGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Set new guildplayer " + guildPlayer.getUUID().toString() + " to guild " + guild.guildUUID.toString());
    }

    public static HashSet<GuildPlayer> get_nearby_guild_player(GuildPlayer gPlayer) {
        HashSet<GuildPlayer> allPlayers = new HashSet<>();
        for (GuildPlayer newGPlayer : GuildDatabase.getAllGuildPlayers(true)) {
            if (gPlayer.getGuild() == newGPlayer.getGuild()) {
                if (gPlayer != newGPlayer) {
                    Player player1 = Bukkit.getPlayer(gPlayer.getUUID());
                    Player player2 = Bukkit.getPlayer(newGPlayer.getUUID());
                    if (player1.getLocation().getWorld() == player2.getLocation().getWorld()) {
                        if (player1.getLocation().distance(player2.getLocation()) <= 75) {
                            allPlayers.add(newGPlayer);
                        }
                    }
                }
            }
        }
        return allPlayers;
    }

}
