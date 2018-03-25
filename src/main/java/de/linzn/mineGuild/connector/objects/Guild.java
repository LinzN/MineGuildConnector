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

package de.linzn.mineGuild.connector.objects;


import java.util.HashSet;
import java.util.UUID;

public class Guild {
    public String guildName;
    public UUID guildUUID;
    public int guildLevel;
    public HashSet<GuildPlayer> guildPlayers;


    public Guild(String guildName, UUID guildUUID) {
        this.guildName = guildName;
        this.guildUUID = guildUUID;
        this.guildLevel = 1;
        this.guildPlayers = new HashSet<>();
    }


    public void setGuildPlayer(GuildPlayer guildPlayer) {
        this.guildPlayers.add(guildPlayer);
    }

    public void unsetGuildPlayer(GuildPlayer guildPlayer) {
        this.guildPlayers.remove(guildPlayer);
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void setLevel(int level) {
        this.guildLevel = level;
    }

}