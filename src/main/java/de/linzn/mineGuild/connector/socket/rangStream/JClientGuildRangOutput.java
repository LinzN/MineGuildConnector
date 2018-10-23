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

package de.linzn.mineGuild.connector.socket.rangStream;

import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientGuildRangOutput {

    public static String headerChannel = "mineGuild_rang";


    public static void set_player_rang(UUID actor, String playerName, String rang) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_rang_setplayer");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(playerName);
            dataOutputStream.writeUTF(rang);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void show_rang_info(UUID actor, String rang) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_rang_show_rang_info");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(rang);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void show_player_rang(UUID actor, String player) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_rang_show_player_rang");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(player);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

    public static void list_guild_rangs(UUID actor, int page) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_rang_show_list");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeInt(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput(headerChannel, byteArrayOutputStream.toByteArray());
    }

}
