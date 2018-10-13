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

package de.linzn.mineGuild.connector.utils;

import de.linzn.mineGuild.connector.MineGuildConnectorPlugin;
import de.linzn.mineGuild.connector.socket.updateStream.JClientGuildUpdateOutput;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.UUID;

public class GuildUpdater implements Runnable {
    private HashMap<UUID, Double> guild_cache;

    public GuildUpdater() {
        this.guild_cache = new HashMap<>();
        Bukkit.getScheduler().runTaskTimerAsynchronously(MineGuildConnectorPlugin.inst(), this, 100, 20 * 10);
    }

    public void addData(UUID uuid, double value) {
        if (guild_cache.containsKey(uuid)) {
            double old_value = guild_cache.get(uuid);
            guild_cache.put(uuid, old_value + value);
        } else {
            guild_cache.put(uuid, value);
        }
    }

    @Override
    public void run() {
        MineGuildConnectorPlugin.inst().getLogger().info("Run sync task..: " + guild_cache.size());
        final HashMap<UUID, Double> data = new HashMap<>(guild_cache);
        guild_cache.clear();
        JSONArray jsonArray = new JSONArray();
        for (UUID uuid : data.keySet()) {
            double value = data.get(uuid);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("guildUUID", uuid.toString());
            jsonObject.put("guildExperience", value);
            jsonArray.add(jsonObject);
        }

        JClientGuildUpdateOutput.add_experience_sync(jsonArray);
        MineGuildConnectorPlugin.inst().getLogger().info("Finish sync task..");
    }
}
