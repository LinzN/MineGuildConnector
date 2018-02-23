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

package de.linzn.mineGuild.connector.socket;

import de.linzn.mineSuite.core.MineSuiteCorePlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JClientGuildOutput {


    public static void create_guild(String guildName, UUID creator) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_create_guild");
            dataOutputStream.writeUTF(guildName);
            dataOutputStream.writeUTF(creator.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void remove_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_remove_guild");
            dataOutputStream.writeUTF(actor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void info_guild(UUID actor, String guildArg) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_info_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(guildArg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void members_guild(UUID actor, String guildArg) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_info_guild_members");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(guildArg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void invite_to_guild(UUID actor, String invitedName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_invite_to_guild");
            dataOutputStream.writeUTF(actor.toString());
            dataOutputStream.writeUTF(invitedName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void accept_invite_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_accept_invite_guild");
            dataOutputStream.writeUTF(actor.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

    public static void deny_invite_guild(UUID actor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("guild_deny_invite_guild");
            dataOutputStream.writeUTF(actor.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MineSuiteCorePlugin.getInstance().getMineJSocketClient().jClientConnection1.writeOutput("mineGuild", byteArrayOutputStream.toByteArray());
    }

}
