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


import java.util.UUID;

public class GuildPlayer {
    private Guild guild;
    private UUID playerUUID;

    public GuildPlayer(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public UUID getUUID() {
        return this.playerUUID;
    }


}