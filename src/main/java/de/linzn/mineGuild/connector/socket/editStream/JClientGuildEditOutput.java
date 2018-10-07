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

package de.linzn.mineGuild.connector.socket.editStream;

import de.linzn.mineSuite.core.MineSuiteCorePlugin;
import org.bukkit.Location;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientGuildEditOutput {

    public static String headerChannel = "mineGuild_edit";


    public static void set_guild_name(UUID actor, String guildName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_edit_guild_name");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(guildName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void set_guild_master(UUID actor, String playerName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_edit_guild_master");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void set_player_rang(UUID actor, String playerName, String rang) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_edit_setrang_player");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(playerName);
            dataOutputStream.writeUTF(rang);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void set_guild_home(UUID actor, Location location) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_edit_guild_home");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(MineSuiteCorePlugin.getInstance().getMineConfigs().generalConfig.BUNGEE_SERVER_NAME);
            dataOutputStream.writeUTF(location.getWorld().getName());
            dataOutputStream.writeDouble(location.getX());
            dataOutputStream.writeDouble(location.getY());
            dataOutputStream.writeDouble(location.getZ());
            dataOutputStream.writeFloat(location.getYaw());
            dataOutputStream.writeFloat(location.getPitch());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }


}
