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
import de.linzn.mineGuild.connector.events.GuildCreateEvent;
import de.linzn.mineGuild.connector.events.GuildDisbandEvent;
import de.linzn.mineGuild.connector.events.GuildUpdateEvent;
import de.linzn.mineGuild.connector.objects.Guild;
import de.linzn.mineGuild.connector.objects.GuildPlayer;
import de.linzn.mineGuild.connector.utils.LanguageDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class GuildConnectorManager {

    public static void add_guild(Guild guild, UUID creator, String sourceServer) {
        GuildDatabase.addGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Create new guild " + guild.guildUUID.toString());
        add_guildplayer(guild.guildUUID, creator);
        GuildCreateEvent guildCreateEvent = new GuildCreateEvent(guild, sourceServer);
        if (guildCreateEvent.isSourceServer()) {
            TransactionManager.create_guild_account(guild.guildUUID);
        }
        MineGuildConnectorPlugin.inst().getServer().getPluginManager().callEvent(guildCreateEvent);
        MineGuildConnectorPlugin.inst().getLogger().info("Fired GuildCreateEvent");
    }

    public static void remove_guild(UUID guildUUID, String sourceServer) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        if (guild != null) {
            for (GuildPlayer guildPlayer : guild.guildPlayers) {
                if (Bukkit.getServer().getOfflinePlayer(guildPlayer.getUUID()).isOnline()) {
                    TransactionManager.player_unset_prefix(Bukkit.getServer().getPlayer(guildPlayer.getUUID()));
                }
            }
        }
        GuildDatabase.removeGuild(guildUUID);
        MineGuildConnectorPlugin.inst().getLogger().info("Disband guild " + guildUUID.toString());
        GuildDisbandEvent guildDisbandEvent = new GuildDisbandEvent(guild, sourceServer);
        if (guildDisbandEvent.isSourceServer()) {
            TransactionManager.delete_guild_account(guildUUID);
        }
        MineGuildConnectorPlugin.inst().getServer().getPluginManager().callEvent(guildDisbandEvent);
        MineGuildConnectorPlugin.inst().getLogger().info("Fired GuildDisbandEvent");
    }

    public static void update_guild(UUID guildUUID, int level) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        guild.setLevel(level);
        MineGuildConnectorPlugin.inst().getLogger().info("Updated guild " + guild.guildUUID.toString() + " LVL: " + level);
        GuildUpdateEvent guildUpdateEvent = new GuildUpdateEvent(guild, level);
        MineGuildConnectorPlugin.inst().getServer().getPluginManager().callEvent(guildUpdateEvent);
        MineGuildConnectorPlugin.inst().getLogger().info("Fired GuildUpdateEvent");
    }

    public static void add_guildplayer(UUID guildUUID, UUID playerUUID) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        GuildPlayer guildPlayer = new GuildPlayer(playerUUID);
        guild.setGuildPlayer(guildPlayer);
        guildPlayer.setGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Add new guildplayer " + guildPlayer.getUUID().toString() + " to guild " + guild.guildUUID.toString());
        if (Bukkit.getServer().getOfflinePlayer(playerUUID).isOnline()) {
            TransactionManager.player_set_prefix(Bukkit.getServer().getPlayer(playerUUID));
        }
    }

    public static void remove_guildplayer(UUID guildUUID, UUID playerUUID) {
        Guild guild = GuildDatabase.getGuild(guildUUID);
        GuildPlayer guildPlayer = GuildDatabase.getGuildPlayer(playerUUID);
        guild.unsetGuildPlayer(guildPlayer);
        MineGuildConnectorPlugin.inst().getLogger().info("Removed new guildplayer " + playerUUID.toString() + " from guild " + guild.guildUUID.toString());
        if (Bukkit.getServer().getOfflinePlayer(playerUUID).isOnline()) {
            TransactionManager.player_unset_prefix(Bukkit.getServer().getPlayer(playerUUID));
        }
    }

    public static GuildPlayer wrap_guild_player(UUID uuid) {
        return GuildDatabase.getGuildPlayer(uuid);
    }


    public static void set_guild_object(JSONObject jsonObject) {
        UUID guildUUID = UUID.fromString((String) jsonObject.get("guildUUID"));
        int guildLevel = Integer.parseInt("" + (long) jsonObject.get("guildLevel"));

        Guild guild = new Guild(guildUUID);
        guild.setLevel(guildLevel);

        JSONArray jsonArray = (JSONArray) jsonObject.get("guildMembers");
        for (Object object : jsonArray) {
            UUID playerUUID = UUID.fromString((String) object);
            GuildPlayer guildPlayer = new GuildPlayer(playerUUID);
            guildPlayer.setGuild(guild);
            guild.setGuildPlayer(guildPlayer);
        }

        GuildDatabase.addGuild(guild);
        MineGuildConnectorPlugin.inst().getLogger().info("Set new guild from JSON" + guild.guildUUID.toString());
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

    public static void requestGuildConfirm(UUID playerUUID, UUID guildUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            MineGuildConnectorPlugin.inst().getLogger().info("Player is offline? Tja tadl???");
            return;
        }
        Guild guild = GuildDatabase.getGuild(guildUUID);
        if (guild == null) {
            MineGuildConnectorPlugin.inst().getLogger().info("Guild is null? How can this happens???");
            return;
        }
        GuildDatabase.guildActionRequests.put(playerUUID, guild);
        player.sendMessage(LanguageDB.ACTION_WARNING);

        int counter = 0;
        while (GuildDatabase.guildActionRequests.containsKey(playerUUID)) {
            try {
                Thread.sleep(70);
            } catch (InterruptedException ignored) {
            }
            if (counter >= 100) { /* 5000 ms cancel task */
                GuildDatabase.guildActionRequests.remove(playerUUID);
                return;
            }
            counter++;
        }
    }

}
